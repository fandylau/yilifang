/**
 * liuhuan超级附件上传回显插件（基于WebUploader的二次封装）
 * 支持word、excel、ppt、视频、txt文件的在线查看或播放
 * 支持附件拖拽上传、qq微信截图粘贴
 * 基于html5 不支持ie6/7/8/9 
 * Author:Fandy Liu
 * example:
 * <!-- 每个插件定义时 dom的id必填 如下面的 id='uploader' 页面中有多个上传组件时 id必须不同 
 *<div id="uploader"  class='easyui-poweruploader' 
 *   style='margin-left:15px;margin-top:15px;border:1px solid blue;' 
 *   data-options = "
 *     name:'model.name',                               //表单的name
 *     value:''                                         //表单value（附件回填时用）
 *     width:700,                                      //容器宽（选填）默认700
 *     height:300,                                     //容器高（选填） 默认300
 *     isView:false,                                   //是否是查看模式   （选填） 
 *     fileNumLimit:10,                                //允许文件总数量, 超出则不允许加入队列 默认10（选填）  
 *     fileSingleSizeLimit: 50*1024*1024,              //验证单个文件大小是否超出限制, 超出则不允许加入队列 默认50M
 *     formData :{folder:'test'},                      //文件存放路径
 *     canDnd:true,                                    //是否支持拖拽上传 默认true 
 *     extensions: '*',                                //限制上传的格式 （选填） 默认所有文件  做限制时 格式：gif,jpg,jpeg,bmp,png
 *     onBeforeSelect:function(file){ alert('onBeforeSelect'); return true;}, //文件选中时监听 。返回一个boolean  true 文件放进队列  false文件选择无效
 *     onUpSuccess:function(file){alert(file.name+'上传完成了啊！')},             //单个文件上传成功时监听    file.dataid 数据库的附件id
 *     onUpFinished:function(){alert('所有上传都完成啦！')},                       //上传操作完成时（不管成功或失败）的监听
 *     onRemoveComplete:function(file){alert(file.name+'删除完成了')},          //单个文件删除成功时的监听  
 *     onRemoveAllComplete:function(){alert('所有文件都删除完成了')},              //所有文件都删除的监听  
 *     onUpError:function(file){alert('文件'+file.name+'上传出错了');}           //上传失败后的监听    
 *   "></div>
 */
(function ($) {
	
    //移除数组中的某个元素
	Array.prototype.remove = function(val) {
		var index = this.indexOf(val);
		if (index > -1) {
			this.splice(index, 1);
		}
	};
	
	//全局路径
    var applicationPath =(function(){
    	    var strFullPath = window.document.location.href;
    	    var strPath = window.document.location.pathname;
    	    var pos = strFullPath.indexOf(strPath);
    	    var prePath = strFullPath.substring(0, pos);
    	    var postPath = strPath.substring(0, strPath.substr(1).indexOf('/') + 1);
    	    return (prePath + postPath);
    })(); 
    
    // 随机数字
    var randomNum = function () {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    };
    
	// 文件大小字符如50M
	var converFileSize = function(limit) {
		var size = "";
		if (limit < 0.1 * 1024) { // 如果小于0.1KB转化成B
			size = limit.toFixed(2) + "B";
		} else if (limit < 0.1 * 1024 * 1024) {// 如果小于0.1MB转化成KB
			size = (limit / 1024).toFixed(2) + "KB";
		} else if (limit < 0.1 * 1024 * 1024 * 1024) { // 如果小于0.1GB转化成MB
			size = (limit / (1024 * 1024)).toFixed(2) + "MB";
		} else { // 其他转化成GB
			size = (limit / (1024 * 1024 * 1024)).toFixed(2) + "GB";
		}
		var sizestr = size + "";
		var len = sizestr.indexOf("\.");
		var dec = sizestr.substr(len + 1, 2);
		if (dec == "00") {// 当小数点后为00时 去掉小数部分
			return sizestr.substring(0, len) + sizestr.substr(len + 3, 2);
		}
		return sizestr;
	};  
	// 取文件后缀名
	var getFileExt = function (filepath) {
		if (filepath != "") {
			var pos = "." + filepath.replace(/.+\./, "");
			return pos;
		}
	}
	// 取文件全名名称
	var getFileName=function(filepath) {
		if (filepath != "") {
			var names = filepath.split("\\");
			return names[names.length - 1];
		}
	};
	//文件名太长时截取
    var getFileShortName = function(fileName){
    	return  fileName.replace(/^(^.{8})(.+)(.{2}\.+\w+$)$/g, "$1...$3");
    } 
    
    //文件类型判断
	var FILETYPE = (function() {
		return {
			isWORD : function(fileExt) {// word类型
				return '.doc,.docx,.wps'.indexOf(fileExt) > -1;
			},
			isEXCEL : function(fileExt) {// word类型
				return '.xls,.xlsx,.et'.indexOf(fileExt) > -1;
			},
			isPPT : function(fileExt) {// word类型
				return '.ppt,.pptx,.dps'.indexOf(fileExt) > -1;
			},
			isTXT : function(fileExt) {// 文本类型
				return '.txt'.indexOf(fileExt) > -1;
			},
			isPDF : function(fileExt) {// 文本类型
				return '.pdf'.indexOf(fileExt) > -1;
			},
			isIMAGE : function(fileExt) {// 图片类型
				return '.jpg,.jpeg,.tiff,.raw,.bmp,.gif,.png'.indexOf(fileExt) > -1;
			},
			isRAR : function(fileExt) {// rar类型
				return '.rar,.zip,.7z,.gz,.bz,.ace,.uha,.uda,.zpaq'.indexOf(fileExt) > -1;
			},
			isVIDEO : function(fileExt) {// 视频类型
				return '.avi,.rmvb,.rm,.asf,.divx,.mpg,.mpeg,.mpe,.wmv,.mp4,.mkv,.vob'.indexOf(fileExt) > -1;
			},
			isAudio : function(fileExt) {// 音频类型
				return '.mp3,.aac,.wav,.wma'.indexOf(fileExt) > -1;
			}
		};
	})();
    
    // 在线预览
    var docViewOnline=function (filepath,realName){
    	alert("我是word文档在线查看，还未实现，尽请期待！")
    }
    //pdf在线预览
    var openPDFView = function(filePath,fileName){
    	alert("我是pdf在线查看，还未实现，尽请期待！")
    	
    } 
    
    //打开视频播放页面
    var openVideoWin = function (filepath,filealias){
    	alert("我是视频在线观看，还未实现，尽请期待！")
    }
   
    //初始化附件视图
    var initView =function(thisObj){
        var thisData = $.data(thisObj, "poweruploader");  
		var thisOptions = thisData.options;    
		var pickerid = "";//选择文件按钮的随机id
        if (!WebUploader.Uploader.support()) {
            var error = "上传控件不支持您的浏览器！请尝试升级flash版本或者使用Chrome引擎的浏览器。<a target='_blank' href='http://se.360.cn'>下载页面</a>";
            if (window.console) {
                window.console.log(error);
            }
            $(thisObj).text(error);
            return;
        }
        var target = $(thisObj);//容器
        if (target.find(".attachment-list").length > 0) {
            return;
        }
        var hdFileData = $("#" + thisOptions.hiddenInputId);
        if (typeof guidGenerator36 != 'undefined'){//给一个唯一ID
        	pickerid = guidGenerator36();
        }
        else{
            pickerid = (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        }
        var uploaderStrdiv = '<div class="attachmentCont">';
            uploaderStrdiv+= '<div id="win_'+$(thisObj).attr('id')+'"  style="padding: 10px;display:none;"></div>';//存放文本文档在线查看内容
        if(thisOptions.name){
        	uploaderStrdiv+='<input type=hidden id="submit_'+$(thisObj).attr('id')+'" name="'+thisOptions.name+'" value="'+thisOptions.value+'"/>';
        }
        if (thisOptions.auto) {
            uploaderStrdiv +=
            '<div class="attachment-tt"><span class="attach-funcs">' 
              +'<a href="javascript:void(0);" id="' + pickerid + '" class="attach-func attach1"><i class="attach-add"></i>选择文件</a>' 
              +'<a href="javascript:void(0);" class="attach-func attach3"><i class="attach-dust"></i>清除全部</a>' 
              +'</span>'
              +'支持'+(thisOptions.extensions=='*'?'所有':thisOptions.extensions)+'类型文件，单个文件最大为'+converFileSize(thisOptions.fileSingleSizeLimit);
              if(thisOptions.fileNumLimit){
            	  uploaderStrdiv +='，最多允许上传'+thisOptions.fileNumLimit+'个文件！';
              }
              uploaderStrdiv +='</div>';
        } else {
            uploaderStrdiv +=
            '<div class="attachment-tt"><span class="attach-funcs">' 
                +'<a href="javascript:void(0);" id="' + pickerid + '"  class="attach-func attach1"><i class="attach-add"></i>选择文件</a>' 
                +'<a href="javascript:void(0);" class="attach-func webuploadbtn"><i class="attach-upload"></i>上传附件</a>'
                +'<a href="javascript:void(0);" class="attach-func attach3"><i class="attach-dust"></i>清除全部</a>' 
                +'</span>'
                +'支持'+(thisOptions.extensions=='*'?'所有':thisOptions.extensions)+'类型文件，单个文件最大为'+converFileSize(thisOptions.fileSingleSizeLimit);
                if(thisOptions.fileNumLimit){
              	  uploaderStrdiv +='，最多允许上传'+thisOptions.fileNumLimit+'个文件！';
                }
                uploaderStrdiv +='</div>';
        }
        uploaderStrdiv += '<div style="display:none" class="UploadhiddenInput" ></div>';
        uploaderStrdiv += '<div class="attachment-list"><ul class="clearfloat sc"></ul></div>';
        uploaderStrdiv += '</div>';
        target.append(uploaderStrdiv);
        target.width(thisOptions.width);
    	target.find('.attachmentCont ul').height(thisOptions.height);
    	if(thisOptions.isView && !thisOptions.value){
    		target.html('无附件上传记录');
    		return ;
    	}
    	if(thisOptions.isView){
    		$(thisObj).find('.attachment-tt').hide();
    	}
        $.data(thisOptions, "pickerid",pickerid);//缓存pickerid
        initWebUpload(thisObj);//生成webuploader
        if(thisOptions.value){//value有值 回显示
        	 renderTheEditFiles(thisObj);
        }
        
    };
    
    //初始化webUploader
	var initWebUpload = function(thisObj){
		   var thisData = $.data(thisObj, "poweruploader");  
		   var thisOptions = thisData.options;  
		   var extentions;
		   if(thisOptions.extensions=='*'){
			   extentions='.*';
		   }else{
			   extentions=('.'+thisOptions.extensions.split(",").join(",."));
		}

		// HOOK 这个必须要再uploader实例化前面
		WebUploader.Uploader.register({
			'before-send-file' : 'beforeSendFile',
			'before-send' : 'beforeSend'
		}, {
			beforeSendFile : function(file) {
				
				// Deferred对象在钩子回掉函数中经常要用到，用来处理需要等待的异步操作。
				var task = new $.Deferred();
				// 根据文件内容来查询MD5
				var uploader =  $.data(thisOptions, "uploader");
				uploader.md5File(file,0, 10 * 1024 * 1024).progress(function(percentage) { // 及时显示进度
					//console.log('计算md5进度:', percentage);
				}).then(function(val) { // 完成
					console.log('md5 result:', val);
					file.md5 = val;
					// 模拟用户id
					file.uid = WebUploader.Base.guid();
					// 进行md5判断
					$.post(applicationPath + "/attach/checkFileMd5", {
						uid : file.uid,
						md5 : file.md5
					},function(data) {
							var status = data.Retdata.value;
							task.resolve();
							if (status == 101) {
								//文件不存在，那就正常流程
							} else if (status == 100) {
								// 忽略上传过程，直接标识上传成功；
								uploader.skipFile(file);
								file.pass = true;
							} else if (status == 102) {
								// 部分已经上传到服务器了，但是差几个模块。
								file.missChunks = data.data;
							}
					});
				});
				return $.when(task);
			},
			beforeSend : function(block) {
				console.log("block")
				var task = new $.Deferred();
				var file = block.file;
				var missChunks = file.missChunks;
				var blockChunk = block.chunk;
				console.log("当前分块：" + blockChunk);
				console.log("missChunks:" + missChunks);
				if (missChunks !== null && missChunks !== undefined && missChunks !== '') {
					var flag = true;
					for (var i = 0; i < missChunks.length; i++) {
						if (blockChunk == missChunks[i]) {
							console.log(file.name + ":" + blockChunk + ":还没上传，现在上传。");
							flag = false;
							break;
						}
					}
					if (flag) {
						task.reject();
					} else {
						task.resolve();
					}
				} else {
					task.resolve();
				}
				return $.when(task);
			}
		});
        
		//分片传递大小默认：50M 必须与后端的配置保持一致
	    var chunkSize = 50 * 1024 * 1024;
		//初始化创建webuploader的参数
		var webuploaderoptions = $.extend({
               // swf文件路径
               swf: applicationPath + '/js/webuploader/Uploader.swf',
               // 文件接收服务端。
               server:thisOptions.server,
               //文件删除地址
               deleteServer: thisOptions.deleteServer,
               // 选择文件的按钮。可选。
               // 内部根据当前运行是创建，可能是input元素，也可能是flash.
               pick: '#' + $.data(thisOptions, "pickerid"),
               //不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
               resize: false,
               //上传时传递的参数
               formData: $.extend({uid: 0,md5: '',chunkSize:chunkSize},thisOptions.formData),
               //开启分片传递 支持超大附件上传
               chunked: true,
               //每次上传的分片大小
               chunkSize: chunkSize, 
               //指定运行时启动顺序。默认会想尝试 html5 是否支持，如果支持则使用 html5, 否则则使用 flash.
               runtimeOrder: 'html5,flash',
             
               //去重， 根据文件名字、文件大小和最后修改时间来生成hash Key true为可重复   ，false为不可重复
   		       duplicate:false,
   		       //指定Drag And Drop拖拽的容器，如果不指定，则不启动。
   		   	   dnd:thisOptions.canDnd&&!thisOptions.isView?'.attachmentCont':undefined,
   		   	   //是否禁掉整个页面的拖拽功能，如果不禁用，图片拖进来的时候会默认被浏览器打开。		   
   		   	   disableGlobalDnd:true,
   		       //粘贴截图
   		       paste: document.body,
   		       //是否实时删除文件，如果实时删除则请求删除文件ajax接口 如果不是实时删除,则记录删除文件,默认true
   		       isRealTimeDelete: true,
   		       //是否自定义添加文件错误事件
   		       eventAddFileErrorCustom:false,
   		       //上传并发数。允许同时最大上传进程数。目前暂时设置一个。设置多个回显文件顺序不对（数据库依据文件创建时间排序多线程会错乱）
               threads:1,
               //上传总文件数量限制
               fileNumLimit: thisOptions.fileNumLimit,
               //总文件大小数量限制
               fileSizeLimit: thisOptions.fileSizeLimit,
               //单个文件大小限制
               fileSingleSizeLimit: thisOptions.fileSingleSizeLimit,
               //限制文件类型,格式gif,jpg,jpeg,bmp,png
               accept: {
                   title: 'IntoTypes',
                   extensions:thisOptions.extensions,
                   mimeTypes: extentions
               }
           },thisOptions);
           var uploader = WebUploader.create(webuploaderoptions);
           $.data(thisOptions, "uploader",uploader);//存储（一个页面多个上传控件时使用）
           $.data(thisOptions, "dbFileIds",new Array());//存储  已上传附件id的数组
           $.data(thisOptions, "audios",new Array());//存储 音频
           initUploaderEvent(thisObj);//初始化事件
	};
	//构建不同文件类型的图块
	var buildFileHtml=function(thisObj,file){
		 var thisData = $.data(thisObj, "poweruploader");  
		 var thisOptions = thisData.options;    
		 var uploader = $.data(thisOptions, "uploader");
		 var target = $(thisObj);//容器
		 var $list = target.find('.attachment-list ul'),
		 $li = $('<li>'
			        +'<div class="attachPrev" id="'+$(thisObj)[0].id + file.id+ '">'
			             +'<a href="javascript:void(0);" class="attach-cls"></a>'
			        +'</div>'
			        +'<div class="attachName" title='+file.name+'>'+getFileShortName(file.name)+'</div>' 
			     +'</li>');
	     $list.append($li);
	     //判断判断文件类型
	     var fileExt = getFileExt(file.name).toLowerCase();
	     if(FILETYPE.isIMAGE(fileExt)){//图片
	    	 $li.find('.attachPrev').addClass('attach-img').append('<img src="'+applicationPath+'/js/extends/poweruploader/images/attach_img.png"/>');
	    	 var $img = $li.find('img');
	    	 var ratio = window.devicePixelRatio || 1;
	    	 // 缩略图大小
	         var thumbnailWidth = 110 * ratio
	         var thumbnailHeight = 110 * ratio
             uploader.makeThumb(file, function (error, src) {//压缩一把图片
                if (error) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }
                 $img.attr('src', src);
             },thumbnailWidth, thumbnailHeight);
	    	 if(file.statusText=='complete'){//上传完成
	    		 $img.attr('data-original',applicationPath+file.orginalPath);
	    		 bindImgeViewer(thisObj);//绑定图片查看
	    	 }
	     }else if(FILETYPE.isWORD(fileExt)){//word
	    	$li.find('.attachPrev').addClass('attach-doc');
	     }else if(FILETYPE.isEXCEL(fileExt)){//excel
	    	$li.find('.attachPrev').addClass('attach-xls');
	     }else if(FILETYPE.isPPT(fileExt)){//ppt
	    	$li.find('.attachPrev').addClass('attach-ppt');
	     }else if(FILETYPE.isRAR(fileExt)){//rar
	    	$li.find('.attachPrev').addClass('attach-rar');
	     }else if(FILETYPE.isPDF(fileExt)){//pdf
	    	$li.find('.attachPrev').addClass('attach-pdf'); 
	     }else if(FILETYPE.isTXT(fileExt)){//pdf
	    	$li.find('.attachPrev').addClass('attach-txt'); 
	     }else if(FILETYPE.isVIDEO(fileExt)){//视频
	        $li.find('.attachPrev').addClass('attach-video'); 
	     }else if(FILETYPE.isAudio(fileExt)){//视频
	        $li.find('.attachPrev').addClass('attach-audio'); 
	     }else{
	    	$li.find('.attachPrev').addClass('attach-file');
	     }
	};
	
	//初始化webuploader事件
	initUploaderEvent=function(thisObj){
		 var thisData = $.data(thisObj, "poweruploader");  
		 var thisOptions = thisData.options;    
		 var target = $(thisObj);//容器
		 var uploader = $.data(thisOptions, "uploader");
		 var dbFileIds =$.data(thisOptions, "dbFileIds");
		 var $list = target.find('.attachment-list ul'),
         $btn = target.find('.webuploadbtn'),//手动上传按钮备用
         state = 'pending',
         $hiddenInput = target.find('.attachment-list');
         //添加队列监听
         uploader.on('fileQueued', function (file) {
        	 buildFileHtml(thisObj,file);//
  	     });
         //添加队列前的错误验证，返回false的事件阻止文件上传
         uploader.on( 'beforeFileQueued', function(file) {
                thisOptions.onBeforeSelect.call(thisObj);//添加监听
				//验证最大允许上传大小
				if(file.size > thisOptions.fileSingleSizeLimit){
					alert('超出单个文件大小限制，最大允许上传大小为：'+converFileSize(thisOptions.fileSingleSizeLimit));
					return false;
				}
				//验证所有上传数量
				var size = target.find('.attachment-list ul li').length;
				if(size>=thisOptions.fileNumLimit){
					alert('超出数量限制，最大上传数量为：'+thisOptions.fileNumLimit+'个');
					return false;
				}
				//验证文件上传重复
				var attach = target.find('.attachment-list ul li').find('.attachName');
				for(var i = 0;i<attach.length;i++){
					var filename = $(attach[i]).attr('title');
					if(filename ==file.name ){
						alert('文件['+file.name +']已经上传了，不允许重复上传');
						return false;
					}
				}
		 });
         //自动上传设置了
         if (thisOptions.auto) {
             uploader.upload();
         }
         
        //当某个文件的分块在发送前触发，主要用来询问是否要添加附带参数，大文件在开起分片上传的前提下此事件可能会触发多次。
        uploader.on('uploadBeforeSend',function(obj, data){
             var file = obj.file;
             data.md5 = file.md5 || '';
             data.uid = file.uid;
        });
         
     	// 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
             var $li = $('#' + $(thisObj)[0].id + file.id)
             ,$percent = $li.find('.attach-loading').find('span');
             // 避免重复创建
             if (!$percent.length) {
                 $percent = $('<div class="attach-loading"><p><span></span></p></div>').appendTo($li).find('span');
             }
             $percent.css('width', percentage * 100 + '%');
         });
         // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file, response) {
             $('#' + $(thisObj)[0].id + file.id).addClass('upload-state-done');
             var $li = $('#' + $(thisObj)[0].id + file.id),$error = $li.find('.attach-success');
             // 避免重复创建
             if (!$error.length) {
                 $error = $('<div class="attach-success"></div>').appendTo($li);
             }
            
             $error.text('上传完成');
      
             /*$hiddenInput.append('<input type="hidden" id="hiddenInput' + $(thisObj)[0].id + file.id + '" class="hiddenInput" value="' + response.id + '" />');   //添加隐藏域
             dbFileIds.push(response.id);//数据库已有的附件id
             $('#submit_'+$(thisObj)[0].id).val(dbFileIds.join(',')); //更新上传表单的值
             //构建下载链接
             var downLoadUrl = thisOptions.dowloadServer+'?fileId='+response.id;
             $('#' + $(thisObj)[0].id + file.id).parent()
              .find('.attachName').html('<a href="'+downLoadUrl+'">'+getFileShortName(response.fileName)+'</a>');
             var filePath = response.filepath.replace(/\\/g,"/")+"/"+response.filealias;
             file.orginalPath = filePath;//设置附件的服务器路径
             file.dataid = response.id;
             //如果是图片 添加图片的服务器地址 绑定图片查看
             var $img = $li.find('img');
	    	 if($img.length>0){
	    		// alert(response.filepath)
	    		 $img.attr('data-original',applicationPath+filePath);
	    		 //绑定图片查看
                 bindImgeViewer(thisObj);
	    	 }
	    	 //绑定doc在线查看
             bindWordViewer(thisObj,file);
             //绑定pdf在线预览
     		 bindPDFViewer(thisObj,file);
     		 //绑定视频播放
     		 bindVIDEOViewer(thisObj,file);
     		 //绑定音频
     		 bindAUDIOViewer(thisObj,file);
     		 //绑定txt文本查看
     		 bindTXTViewer(thisObj,file);
     		 //添加监听
     		 if(!file.statusText){
      			thisOptions.onUpSuccess.call(thisObj,file);
      		 }*/
            
         });
         // 文件上传失败，显示上传出错。
         uploader.on('uploadError', function (file) {
             var $li = $('#' + $(thisObj)[0].id + file.id),
                 $error = $li.find('.attach-fail');
             // 避免重复创建
             if (!$error.length) {
                 $error = $('<div class="attach-fail"></div>').appendTo($li);
             }
             $error.text('上传失败');
             thisOptions.onUpError.call(thisObj,file);//添加监听
         });
         // 完成上传完了，成功或者失败，先删除进度条。
         uploader.on('uploadComplete', function (file, response) {
             $('#' + $(thisObj)[0].id + file.id).find('.attach-loading').remove();
         });
         uploader.on('uploadFinished', function () {
        	 thisOptions.onUpFinished.call(thisObj);//添加监听
         });
         
         //删除时执行的方法
         uploader.on('fileDequeued', function (file) {
             var fileId = $("#hiddenInput" + $(thisObj)[0].id + file.id).val();
             if (fileId != null) {
                 $.post(thisOptions.deleteServer,{ fileId: fileId },function (data) {
                	   //删除数据库中的id
                	   dbFileIds.remove(fileId);
                	   $('#submit_'+$(thisObj).attr('id')).val(dbFileIds.join(','));
                	   thisOptions.onRemoveComplete.call(thisObj,file);//添加监听
                	  
                 });
             }
             $("#" + $(thisObj)[0].id + file.id).parent().fadeOut("slow",function (e){
            	 //停止所有音频文件的播放
            	 if(FILETYPE.isAudio(getFileExt(file.name).toLowerCase())){
            		   var audios = $.data(thisOptions, "audios");//缓存中取出
                       for(var i = 0;i<audios.length;i++){
                    	  //alert(audios[i].pos+'##'+ $(thisObj)[0].id + file.id);
                    	  if( audios[i].pos == $(thisObj)[0].id + file.id){
                    		  audios[i].currentTime=0;
  						      audios[i].pause();
  							  $('#'+audios[i].pos).removeClass('on');
                    	  }
                       }           		 
            	 }
            	 $(this).remove();
            	 bindImgeViewer(thisObj);//重新绑定图片信息
             });
             $("#hiddenInput" + $(thisObj)[0].id + file.id).remove();
         });
         //出错提示
         uploader.on("error", function (type) {
             if (type == "Q_EXCEED_NUM_LIMIT") {//上传文件数量超过限制
            	 alert('超出数量限制，最大上传数量为'+thisOptions.fileNumLimit+'个文件');
             }else if(type == "F_EXCEED_SIZE") {//单个文件大小超过限制
            	 alert('单个文件允许上传'+converFileSize(thisOptions.fileSingleSizeLimit));
             }else if(type =="Q_TYPE_DENIED") {//单个文件大小超过限制
            	 alert('只支持'+thisOptions.extensions+'类型文件');
             }else if(type =="F_DUPLICATE") {//上传文件重复
            	 alert('该文件已经在上传队列中了');
             }else {
            	 alert("上传出错！请检查后重新上传！错误代码："+type);
             }
         });
         
         //监听所有操作
         uploader.on('all', function (type) {
             if (type === 'startUpload') {
                 state = 'uploading';
             } else if (type === 'stopUpload') {
                 state = 'paused';
             } else if (type === 'uploadFinished') {
                 state = 'done';
             }
             if (state === 'uploading') {
                 $btn.text('暂停上传');
             } else {
                 $btn.text('开始上传');
             }
         });
         

         //多文件点击上传的方法
         $btn.on('click', function () {
             if (state === 'uploading') {
                 uploader.stop();
             } else {
                 uploader.upload();
             }
         });

         //删除
         $list.on("click", ".attach-cls", function (event) {
        	 var $ele = $(this);
             var id = $ele.parent().attr("id");
        	 $.easyui.messager.confirm('询问', '您确定要删除吗？', function(r) {
        		 if(r){
                     id = id.replace($(thisObj)[0].id, "");
                     var file =  uploader.getFile(id);
                     uploader.removeFile(file,true);
                  
        		 }
        	 });
         });
         //删除全部
         $(thisObj).find('.attach3').on('click',function(){
        	 if($list.find('.attach-cls').length>0){
        		 $.easyui.messager.confirm('询问', '您确定要删除全部附件吗？', function(r) {
            		 if(r){
            			 var eachcount=0;
            			 $list.find('.attach-cls').each(function(){
            				 eachcount++
                    		 var $ele = $(this);
                             var id = $ele.parent().attr("id");
                             id = id.replace($(thisObj).attr('id'), "");
                             var file = uploader.getFile(id);
                             uploader.removeFile(file,true);
                    	 });
            			 
            			 if(eachcount>=$list.find('.attach-cls').length){
            				 $('#submit_'+ $.data(thisOptions, "pickerid")).val('');//清空input的值
            				 thisOptions.onRemoveAllComplete.call(thisObj);//添加监听
            	         }
            		 }
            	 });
        	 }
         });
	};
	//上传成功时的一些操作（用于附件回显时设置附件的html页面）
	var setSuccess = function (thisObj,file,isView){
		 var thisData = $.data(thisObj, "poweruploader");  
		 var thisOptions = thisData.options;  
		 if(!isView){//不是查看状态
			$('#' + $(thisObj)[0].id + file.id).addClass('upload-state-done');
	        var $li = $('#' + $(thisObj)[0].id + file.id),
	            $error = $li.find('.attach-success');
	        // 避免重复创建
	        if (!$error.length) {
	            $error = $('<div class="attach-success">上传完成</div>').appendTo($li);
	        }
		 }else{//隐藏上传按钮
			//$(thisObj).find('.attachment-tt').hide();
			//文件上的删除按钮清空
			$(thisObj).find(".attach-cls").remove();
			//
			$(thisObj).find(".attachmentCont").addClass('attachCheck');
		 }
		 //隐藏域的设置
		 var $hiddenInput = $(thisObj).find('.attachment-list');
		 $hiddenInput.append('<input type="hidden" id="hiddenInput' + $(thisObj)[0].id + file.id + '" class="hiddenInput" value="' + file.dataid + '" />')
	     //构建下载链接
         var downLoadUrl = thisOptions.dowloadServer+'?fileId='+file.dataid;
         $('#' + $(thisObj)[0].id + file.id).parent()
          .find('.attachName').eq(0).html('<a href="'+downLoadUrl+'">'+getFileShortName(file.name)+'</a>');
	    //绑定图片查看
		//bindImgeViewer(thisObj);
		//绑定word在线预览
		bindWordViewer(thisObj,file);
		//绑定pdf在线预览
		bindPDFViewer(thisObj,file);
		//绑定视频播放
		bindVIDEOViewer(thisObj,file);
		//绑定音频
		bindAUDIOViewer(thisObj,file);
		//绑定txt文本查看
		bindTXTViewer(thisObj,file);
	
	}
	//绑定图片查看
	var bindImgeViewer = function(thisObj){
	    var thisData = $.data(thisObj, "poweruploader");  
		var thisOptions = thisData.options;  
		if(!$.data(thisOptions,"viewer")){
			var viewer = $(thisObj).find('.attachment-list').eq(0).viewer({ url: 'data-original'});
		    $.data(thisOptions, "viewer",viewer);  //存储
		}else{
		    $.data(thisOptions,"viewer").viewer('update');//更新
		}
	}
	//绑定word在线预览
	var bindWordViewer = function(thisObj,file){
		//判断是否是word类型
		var fileExt = getFileExt(file.name).toLowerCase();
		if(FILETYPE.isWORD(fileExt)||FILETYPE.isEXCEL(fileExt)||FILETYPE.isPPT(fileExt)){
			$('#' + $(thisObj)[0].id + file.id).on('click',function(e){
				if(e.target==this){
					docViewOnline(file.orginalPath,file.name);
				}
			}).css('cursor','pointer').attr('title','在线查看');
		}
	}
	
	//绑定pdf在线预览
	var bindPDFViewer = function(thisObj,file){
		//判断是否是word类型
		var fileExt = getFileExt(file.name).toLowerCase();
		if(FILETYPE.isPDF(fileExt)){
			$('#' + $(thisObj)[0].id + file.id).on('click',function(e){
				if(e.target == this){
				   //window.open(applicationPath+file.orginalPath,"newWindow","menubar=0,scrollbars=1, resizable=1,status=1,titlebar=0,toolbar=0,location=1");
				   openPDFView(applicationPath+file.orginalPath,file.name);
				}
			}).css('cursor','pointer').attr('title','在线查看');;
		}
	}
	
	//绑定视频在线查看
	var bindVIDEOViewer = function(thisObj,file){
		var fileExt = getFileExt(file.name).toLowerCase();
		if(FILETYPE.isVIDEO(fileExt)){
			var filePath = file.orginalPath;
			var pos=filePath.lastIndexOf("/");
			var filealalis=filePath.substring(pos+1);//文件别名  
			var filePre = filePath.substring(0,pos);
			var appName = applicationPath.substring(applicationPath.lastIndexOf("/")+1);
			$('#' + $(thisObj)[0].id + file.id).on('click',function(e){
				if(e.target.className == 'attach-play'){
					openVideoWin(filePre,filealalis); 
				}
			});
			setTimeout(function(){
				$('#' + $(thisObj)[0].id + file.id)
				  .removeClass('attach-video')
				  .css('background','#f2f7ff url("/'+appName+filePre+'/thumb/thumb_'+filealalis.substring(0,filealalis.lastIndexOf("."))+'.jpg") no-repeat 50%')
				  .css('background-size','80px 80px')
				  .css('cursor','pointer')
				  .attr('title','播放')
				  .append('<a href="javascript:void(0);" class="attach-play"></a>');
			},600)
		}
	}
	//绑定音频在线查看
	var bindAUDIOViewer = function(thisObj,file){
		var thisData = $.data(thisObj, "poweruploader");  
		var thisOptions = thisData.options;  
		var fileExt = getFileExt(file.name).toLowerCase();
		var audios = $.data(thisOptions, "audios");  
		if(FILETYPE.isAudio(fileExt)){
			var filePath = applicationPath+file.orginalPath;
			var audio=new Audio(filePath);
			audio.pos = $(thisObj)[0].id + file.id;
			var areadyHave = false;
			for(var i = 0 ;i<audios.length;i++){
				if(audio.pos == audios[i].pos){
					areadyHave = true;
					break;
				}
			}
			if(!areadyHave){
				audios.push(audio);
			}
			$('#' + $(thisObj)[0].id + file.id).on('click',function(e){
				//alert(e.target.className)
				if(e.target.className=='attach-play' ){
					if(audio.currentTime==0){
						for(var i = 0 ;i<audios.length;i++){
							audios[i].currentTime=0;
							audios[i].pause();
							$('#'+audios[i].pos).removeClass('on');
						}
						$(this).addClass('on');
						setTimeout(function () {//150毫秒后播放
						    audio.play();
						}, 150);
						var that = this;
						audio.addEventListener('ended', function () {  //播放完成监听
							audio.pause();
							audio.currentTime=0;
							$(that).removeClass('on');
						}, false);
					}else{
						audio.pause();
						audio.currentTime=0;
						$(this).removeClass('on');
					}
					
				}
			});
			setTimeout(function(){
				$('#' + $(thisObj)[0].id + file.id)
				  .addClass('')
				  .css('cursor','pointer')
				  .attr('title','播放')
				  .append('<a href="javascript:void(0);" class="attach-play"></a>');;
			},600)
		}
	}
	
	//绑定txt在线预览
	var bindTXTViewer = function(thisObj,file){
		//判断是否是word类型
		var fileExt = getFileExt(file.name).toLowerCase();
		if(FILETYPE.isTXT(fileExt)){
			$('#' + $(thisObj)[0].id + file.id).on('click',function(e){
				if(e.target == this){
					alert("我是Txt文档在线预览，还未实现，敬请期待")
				}
				
			}).css('cursor','pointer').attr('title','在线查看');;
		}
	}

	// 回显已经上传的附件
	var renderTheEditFiles =function(thisObj){
		 var thisData = $.data(thisObj, "poweruploader");  
		 var thisOptions = thisData.options;  
		 var uploader = $.data(thisOptions, "uploader");  
		 var dbFileIds = $.data(thisOptions, "dbFileIds");  
		 if(thisOptions.value){//附件id不为空
			//初始化提交值
			 var arr = thisOptions.value.split(','); 
			 for(var i=0;i<arr.length;i++){
				 dbFileIds.push(arr[i]);
			 }
			 $('#submit_'+$(thisObj).attr('id')).val(dbFileIds.join(','));
			// 从数据库读取已上传的文件
			$.ajax({ 
			       type: 'post', 
			       url: thisOptions.listServer, 
			       data:{fileIds:thisOptions.value},
			       cache:false, 
			       async:false, 
			       dataType: 'json', 
			       success: function(data){ 
			    	   var getFileBlob = function(url, cb) {
							var xhr = new XMLHttpRequest();
							var _true = xhr.open("GET", (url
									.startsWith('http://') || url
									.startsWith('https://')) ? url
									: that.options.fileServerPrefix + url);
							xhr.responseType = "blob";
							xhr.addEventListener('load', function() {
								cb(xhr.response);
							});
							xhr.send();
						};
						var blobToFile = function(blob, name) {
							blob.lastModifiedDate = new Date();
							blob.name = name;
							return blob;
						};
						var getFileObject = function(filePathOrUrl, cb,filename) {
							getFileBlob(filePathOrUrl, function(blob) {
								cb(blobToFile(blob, filename));
							});
					    };
					    var addFiles = new Array();
			    	    $.each(data, function(index, item) {
			    	    	var filePath = item.filepath.replace(/\\/g,"/")+"/"+item.filealias;
		    	    	    getFileObject(applicationPath+filePath, function (fileObject) {
			    	    		var wuFile = new WebUploader.Lib.File(WebUploader.guid('rt_'),fileObject);
								var file = new WebUploader.File(wuFile);
								file.statusText='complete';
								file.setStatus('complete');
								file.dataid = item.id;
								file.orginalPath = filePath;
								addFiles.push(file);
								var retFiles = new Array();
								if(addFiles.length == data.length){
									for(var i= 0;i<data.length;i++){//排序 否则回填的顺序不正确
										for(var j = 0;j<addFiles.length;j++){
											if(addFiles[j].dataid==data[i].id){
												retFiles.push(addFiles[j]);
											}
										}
									}
									for(var i= 0;i<retFiles.length;i++){//排序 否则回填的顺序不正确
									    uploader.addFile(retFiles[i]);
									    setSuccess(thisObj,retFiles[i],thisOptions.isView);
									}
								}
			    	    	},item.filename);
					    });
			    	   
			       } 
			});
		 }
	}
	//初始化组件的时候调用此方法  
    $.parser.plugins.push("poweruploader");//注册扩展组件  
    $.fn.poweruploader = function(options, param){  
    	if (typeof options == "string") { //当options为字符串时，说明执行的是该插件的方法。
    	     return $.fn.poweruploader.methods[options](this, param);  
    	}
    	options = options || {};  
    	return this.each(function() {
    		var thisData = $.data(this, "poweruploader");  
    		var opts = $.extend({},$.parser.parseOptions(this),options);
    		if(opts){
    		    thisData = $.data(this, "poweruploader", { options : $.extend( {},$.fn.poweruploader.defaults,opts)}); 
    		}
    		initView(this);
    	});
    }
	//定义默认属性，初始化的时候可调用  
    $.fn.poweruploader.defaults = {  
         auto: true,
         //width:700,
         //height:300,
         hiddenInputId: "uploadifyHiddenInputId", // input hidden id
         name:'model.name',
         value:'',
         isView:false,
         displayLine:1,
         canDnd:true,//是否可以拖拽上传
         server: applicationPath + '/attach/upload',//上传url地址
         deleteServer: applicationPath+ '/attach/deleteFile',//删除url地址
         dowloadServer: applicationPath+'/attach/downloadFile',//下载url地址
         listServer: applicationPath+'/attach/listFile',
         formData:{},// 文件上传请求的参数表，每次发送都会发送此对象中的参数
         onAllComplete: function (event) { }, // 当所有file都上传后执行的回调函数
         onComplete: function (event) { },// 每上传一个file的回调函数
         fileNumLimit: 10,//验证文件总数量, 超出则不允许加入队列
         fileSizeLimit: undefined,//验证文件总大小是否超出限制, 超出则不允许加入队列。
         fileSingleSizeLimit: 1024*1024*1024,//验证单个文件大小是否超出限制, 超出则不允许加入队列
         PostbackHold: false,
         extensions: '*',//限制上传的格式  格式：gif,jpg,jpeg,bmp,png
         //监听
         onBeforeSelect:function(file){ /*alert('onBeforeSelect'); return true;*/},//设置一个boolean  true 文件放进队列  false文件选择无效
         onUpSuccess:function(file){/*alert(file.name+'上传完成了啊！')*/},//单个文件上传成功时监听    file.dataid 数据库的附件id
         onUpFinished:function(){/*alert('所有上传都完成啦！')*/},//上传操作完成时（不管成功或失败）的监听
         onRemoveComplete:function(file){/*alert(file.name+'删除完成了')*/},//单个文件删除成功时的监听  
         onRemoveAllComplete:function(){/*alert('所有文件都删除了')*/},//所有文件都删除的监听  
         onUpError:function(file){/*alert('文件'+file.name+'上传出错了');*/} //上传失败后的监听    
    }    
    //支持的方法
    $.fn.poweruploader.methods= {
		//新增文件  (数据库id)
    	addFiles:function(thisObj,fileIds){
    		var thisData = $.data(thisObj[0], "poweruploader");
			var thisOptions = thisData.options;
			var arrFileIds = fileIds.split(',');
			var dbFileIds = $.data(thisOptions, "dbFileIds");  
			for(var i=0;i<arrFileIds.length;i++){
				dbFileIds.push(arrFileIds[i]);
			}
			renderTheEditFiles(thisObj[0]);
		},
		removeFiles:function(thisObj,fileIds){
			var thisData = $.data(thisObj[0], "poweruploader");
			var thisOptions = thisData.options;
			var arrFileIds = fileIds.split(',');
			var uploader = $.data(thisOptions, "uploader");  
			var dbFileIds = $.data(thisOptions, "dbFileIds");  
			for(var i=0;i<arrFileIds.length;i++){
				var delId = arrFileIds[i];
	             $.post(thisOptions.deleteServer,{ fileId: delId },function (data) {
	            	   //删除数据库中的id
	            	   var ids =$(thisObj[0]).find('.hiddenInput');
	            	   for(var j = 0;j<ids.length;j++){
	            		   if(delId==$(ids[j]).val()){
	            			   var upId = $(ids[j]).attr('id');
	    	            	   upId=upId.replace(/hiddenInputuploader/,'')
	    	            	   var file = UpdataLoadarrayObj[$(thisObj[0])[0].id].getFile(upId);
	    	                   uploader.removeFile(file,true);
	            		   }
	            	   }
	            	   dbFileIds.remove(arrFileIds[i]);
	            	   $('#submit_'+$(thisObj[0]).attr('id')).val(dbFileIds.join(','));
	             });
	            
			}
		},
		
		removeAll : function(thisObj) {
			var thisData = $.data(thisObj[0], "poweruploader");
			var thisOptions = thisData.options;
			var uploader = $.data(thisOptions, "uploader");  
			var dbFileIds = $.data(thisOptions, "dbFileIds");  
			$(thisObj[0]).find('.attach-cls').each(function() {
				var $ele = $(this);
				var id = $ele.parent().attr("id");
				id = id.replace($(thisObj)[0].id, "");
				var file = uploader.getFile(id);
				uploader.removeFile(file, true);
			});
			$('#submit_'+$(thisObj[0]).attr('id')).val('');
		},
		
		getValues:function(thisObj){
			var thisData = $.data(thisObj[0], "poweruploader");  
			var thisOptions = thisData.options; 
			return $('#submit_'+$(thisObj[0]).attr('id')).val();
		}
    
    }
})(jQuery,window);  