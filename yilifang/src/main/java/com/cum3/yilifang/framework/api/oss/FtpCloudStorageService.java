package com.cum3.yilifang.framework.api.oss;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

/**
 * ftp云存储
 * 
 * @author Fandy Liu
 * @created 2018年9月14日 下午9:55:45
 */
public class FtpCloudStorageService extends CloudStorageService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    /**
     * ftpClient连接池初始化标志
     */
    private boolean hasInit = false;
    /**
     * ftpClient连接池
     */
    private ObjectPool<FTPClient> ftpClientPool;
    /**
     * ftp 根路径 
     */
    private String ftpRootPath;
  

    public FtpCloudStorageService(CloudStorageConfig config) {
        this.config = config;
        this.domain = config.getFtpDomain();
        // 初始化
        init();
    }

    private void init() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setTestOnBorrow(true);
        poolConfig.setTestOnReturn(true);
        poolConfig.setTestWhileIdle(true);
        poolConfig.setMinEvictableIdleTimeMillis(60000);
        poolConfig.setSoftMinEvictableIdleTimeMillis(50000);
        poolConfig.setTimeBetweenEvictionRunsMillis(30000);
        ftpClientPool = new GenericObjectPool<>(new FtpClientPooledObjectFactory(config), poolConfig);
        preLoadingFtpClient(config.getInitialSize(), poolConfig.getMaxIdle());
        ftpRootPath = config.getFtpRootPath();
        hasInit=true;
    }
    
    /**
     * 预先加载FTPClient连接到对象池中
     * 
     * @param initialSize
     *            初始化连接数
     * @param maxIdle
     *            最大空闲连接数
     */
    private void preLoadingFtpClient(Integer initialSize, int maxIdle) {
        if (initialSize == null || initialSize <= 0) {
            return;
        }
        int size = Math.min(initialSize.intValue(), maxIdle);
        for (int i = 0; i < size; i++) {
            try {
                ftpClientPool.addObject();
            } catch (Exception e) {
                log.error("preLoadingFtpClient error...", e);
            }
        }
    }

    /**
     * 上传文件
     * 
     * @param pathname
     *            ftp服务保存地址
     * @param fileName
     *            上传到ftp的文件名
     * @param originfilename
     *            待上传文件的名称（绝对地址） *
     * @return
     * @throws Exception 
     */
    public boolean upload(String pathname, String fileName, String originfilename) throws Exception {
        boolean flag = false;
        InputStream inputStream = null;
        FTPClient ftpClient = getFtpClient();
        try {
            log.info("开始上传文件");
            inputStream = new FileInputStream(new File(originfilename));
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            createDirecroty(pathname, ftpClient);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            boolean b = ftpClient.storeFile(fileName, inputStream);
            flag = true;
            log.info("上传文件"+b);
        } catch (Exception e) {
            log.error("上传文件失败",e.getCause());
            e.printStackTrace();
            throw e;
        } finally {
            releaseFtpClient(ftpClient);
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * 上传文件
     * 
     * @param pathname
     *            ftp服务保存地址
     * @param fileName
     *            上传到ftp的文件名
     * @param inputStream
     *            输入文件流
     * @return
     * @throws Exception 
     */
    public String upload(InputStream inputStream,String pathname, String fileName) throws Exception {
        FTPClient ftpClient = getFtpClient();
        try {
            log.info("开始上传文件");
            ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
            createDirecroty(pathname, ftpClient);
            ftpClient.makeDirectory(pathname);
            ftpClient.changeWorkingDirectory(pathname);
            boolean b = ftpClient.storeFile(fileName, inputStream);
            log.info("上传文件成功"+b);
        } catch (Exception e) {
            log.error("上传文件失败",e.getCause());
            e.printStackTrace();
            throw e;
        } finally {
            releaseFtpClient(ftpClient);
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return config.getFtpDomain()+pathname+"/"+fileName;
    }
   

    /**
     * * 下载文件 *
     * 
     * @param pathname
     *            FTP服务器文件目录 *
     * @param filename
     *            文件名称 *
     * @param localpath
     *            下载后的文件路径 *
     * @return
     * @throws Exception 
     */
    public boolean download(String pathname, String filename, String localpath) throws Exception {
        boolean flag = false;
        OutputStream os = null;
        FTPClient ftpClient = getFtpClient();
        try {
            log.info("开始下载文件");
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(ftpRootPath+pathname);
            FTPFile[] ftpFiles = ftpClient.listFiles();
            for (FTPFile file : ftpFiles) {
                if (filename.equalsIgnoreCase(file.getName())) {
                    File localFile = new File(localpath + "/" + file.getName());
                    os = new FileOutputStream(localFile);
                    ftpClient.retrieveFile(file.getName(), os);
                    os.close();
                    flag = true;
                }
            }
       
            log.info("下载文件成功");
        } catch (Exception e) {
            log.error("下载文件失败");
            e.printStackTrace();
        } finally {
            releaseFtpClient(ftpClient);
            if (null != os) {
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return flag;
    }

    /**
     * * 删除文件 *
     * 
     * @param pathname
     *            FTP服务器保存目录 *
     * @param filename
     *            要删除的文件名称 *
     * @return
     * @throws Exception 
     */
    public boolean delete(String pathname, String filename) throws Exception {
        boolean flag = false;
        FTPClient ftpClient = getFtpClient();
        try {
            log.info("开始删除文件");
            // 切换FTP目录
            ftpClient.changeWorkingDirectory(ftpRootPath+pathname);
            ftpClient.dele(filename);
            ftpClient.logout();
            flag = true;
            log.info("删除文件成功");
        } catch (Exception e) {
            log.error("删除文件失败");
            e.printStackTrace();
        } finally {
            releaseFtpClient(ftpClient);
        }
        return flag;
    }

    /**
     * 按行读取FTP文件
     * 
     * @param remoteFilePath
     *            文件路径（path+fileName）
     * @return
     * @throws Exception 
     */
    public List<String> readFileByLine(String remoteFilePath) throws Exception {
        FTPClient ftpClient = getFtpClient();
        try (InputStream in = ftpClient.retrieveFileStream(encodingPath(ftpRootPath+remoteFilePath));
                BufferedReader br = new BufferedReader(new InputStreamReader(in))) {
            return br.lines().map(line -> StringUtils.trim(line)).filter(line -> StringUtils.isNotEmpty(line))
                    .collect(Collectors.toList());
        } finally {
            ftpClient.completePendingCommand();
            releaseFtpClient(ftpClient);
        }
    }

    /**
     * 获取指定路径下FTP文件
     * 
     * @param remotePath
     *            路径
     * @return FTPFile数组
     * @throws Exception 
     */
    public FTPFile[] retrieveFTPFiles(String remotePath) throws Exception {
        FTPClient ftpClient = getFtpClient();
        try {
            return ftpClient.listFiles(encodingPath(ftpRootPath+remotePath + "/"), file -> file != null && file.getSize() > 0);
        } finally {
            releaseFtpClient(ftpClient);
        }
    }

    /**
     * 获取指定路径下FTP文件名称
     *
     * @param remotePath
     *            路径
     * @return ftp文件名称列表
     * @throws Exception 
     */
    public List<String> retrieveFileNames(String remotePath) throws Exception {
        FTPFile[] ftpFiles = retrieveFTPFiles(remotePath);
        if (ftpFiles.length == 0) {
            return new ArrayList<>();
        }
        return Arrays.stream(ftpFiles).filter(Objects::nonNull).map(FTPFile::getName).collect(Collectors.toList());
    }

    /**
     * 编码文件路径
     * 
     * @param path
     * @return
     * @throws UnsupportedEncodingException
     */
    private static String encodingPath(String path) throws UnsupportedEncodingException {
        // FTP协议里面，规定文件名编码为iso-8859-1，所以目录名或文件名需要转码
        return new String(path.replaceAll("//", "/").getBytes("UTF-8"), "iso-8859-1");
    }

    /**
     * 获取ftpClient
     * 
     * @return
     */
    private FTPClient getFtpClient() {
        checkFtpClientPoolAvailable();
        FTPClient ftpClient = null;
        Exception ex = null;
        // 获取连接最多尝试3次
        for (int i = 0; i < 3; i++) {
            try {
                ftpClient = ftpClientPool.borrowObject();
               // ftpClient.enterLocalPassiveMode();//被动模式不行
                ftpClient.changeWorkingDirectory(ftpRootPath);
                break;
            } catch (Exception e) {
                ex = e;
            }
        }
        if (ftpClient == null) {
            throw new RuntimeException("Could not get a ftpClient from the pool", ex);
        }
        return ftpClient;
    }

    /**
     * 释放ftpClient
     * 
     * @param ftpClient
     * @throws Exception 
     */
    private void releaseFtpClient(FTPClient ftpClient) throws Exception {
        if (ftpClient == null) {
            return;
        }

        try {
            ftpClientPool.returnObject(ftpClient);
        } catch (Exception e) {
            log.error("Could not return the ftpClient to the pool", e);
            // destoryFtpClient
            if (ftpClient.isAvailable()) {
                try {
                    ftpClient.disconnect();
                } catch (IOException io) {
                    io.printStackTrace();
                    throw e;
                }
            }
        }
    }

    /**
     * 检查ftpClientPool是否可用
     * @author Fandy Liu
     * @created 2018年9月21日 下午11:08:31
     */
    private void checkFtpClientPoolAvailable() {
        Assert.state(hasInit, "FTP未启用或连接失败！");
    }

    /**
     * 创建多层目录文件，如果有ftp服务器已存在该文件，则不创建，如果无，则创建
     * 
     * @param remote
     * @param ftpClient
     * @return
     * @throws Exception 
     */
    public boolean createDirecroty(String remote, FTPClient ftpClient) throws Exception {
        boolean success = true;
        String directory = remote + "/";
        // 如果远程目录不存在，则递归创建远程服务器目录
        if (!directory.equalsIgnoreCase("/") && !changeWorkingDirectory(new String(directory), ftpClient)) {
            int start = 0;
            int end = 0;
            if (directory.startsWith("/")) {
                start = 1;
            } else {
                start = 0;
            }
            end = directory.indexOf("/", start);
            String path = "";
            String paths = "";
            while (true) {
                String subDirectory = new String(remote.substring(start, end).getBytes("UTF-8"), "iso-8859-1");
                path = path + "/" + subDirectory;
                if (!existFile(path, ftpClient)) {
                    if (makeDirectory(subDirectory, ftpClient)) {
                        changeWorkingDirectory(subDirectory, ftpClient);
                    } else {
                        log.debug("目录[{}]已存在！",subDirectory);
                        changeWorkingDirectory(subDirectory, ftpClient);
                    }
                } else {
                    changeWorkingDirectory(subDirectory, ftpClient);
                }

                paths = paths + "/" + subDirectory;
                start = end + 1;
                end = directory.indexOf("/", start);
                // 检查所有目录是否创建完毕
                if (end <= start) {
                    break;
                }
            }
        }
        return success;
    }

    /**
     * 改变目录路径
     * 
     * @param directory
     * @param ftpClient
     * @return
     * @throws IOException 
     */
    public boolean changeWorkingDirectory(String directory, FTPClient ftpClient) throws IOException {
        boolean flag = true;
        try {
            flag = ftpClient.changeWorkingDirectory(directory);
            if (flag) {
                log.debug("进入文件夹{} 成功！",directory);

            } else {
                log.debug("进入文件夹{} 失败！开始创建文件夹",directory);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
            throw ioe;
        }
        return flag;
    }

    // 判断ftp服务器文件是否存在
    public boolean existFile(String path, FTPClient ftpClient) throws Exception {
        boolean flag = false;
        try {
            FTPFile[] ftpFileArr = ftpClient.listFiles(path);
            if (ftpFileArr.length > 0) {
                flag = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("端口："+ftpClient.getLocalPort()+"被占用！！！！！！！！！！！！");
            throw e;
        }
        
        return flag;
    }

    // 创建目录
    public boolean makeDirectory(String dir, FTPClient ftpClient) throws Exception {
        boolean flag = true;
        try {
            flag = ftpClient.makeDirectory(dir);
            if (flag) {
                log.debug("创建文件夹{}成功！",dir);

            } else {
                log.debug("文件夹{}已存在！", dir);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("创建文件夹失败！！！",e);
            throw e;
        }
        return flag;
    }
}
