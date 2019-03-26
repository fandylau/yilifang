package com.cum3.yilifang.project.weixin.smallroutine.trade.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cum3.yilifang.framework.web.service.BaseService;
import com.cum3.yilifang.project.weixin.smallroutine.trade.domain.PbsRequire;
import com.cum3.yilifang.project.weixin.smallroutine.trade.domain.PbsSupply;
import com.cum3.yilifang.project.weixin.smallroutine.trade.domain.RequireOrder;

@Service("requireOrderService")
public class RequireOrderService extends BaseService<RequireOrder>{
    /**
     * 订单状态 --未确认
     */
    public static final int  ORDER_STATUS_NOCONFIRMED = 0;
    /**
     * 订单状态 --处理中
     */
    public static final int  ORDER_STATUS_DEALING = 1;
    
    /**
     * 订单状态 --已完成
     */
    public static final int  ORDER_STATUS_FINISHED = 2;
    
    /**
     * 订单状态 --已取消
     */
    public static final int  ORDER_STATUS_CANCLED = 3;
    
    @Autowired
    private  PbsRequireService pbsRequireService;
    @Autowired
    private  PbsSupplyService pbsSupplyService;
    /**
     * 生成订单
     * @author Fandy Liu
     * @created 2018年10月25日 下午10:37:37
     * @return
     */
    public Integer newOrder(){
        RequireOrder order = params().entity(RequireOrder.class);
        order.setOrderSn(this.getOrderIdByUUId());
        order.setOrderStatus(ORDER_STATUS_NOCONFIRMED);
        Integer orderType = order.getOrderType();
        if(orderType ==1){
          //更新需求为已接单
            PbsRequire req = new PbsRequire();
            req.setRecNo(order.getReqId());
            pbsRequireService.recived(req);
        }
        if(orderType ==2){
          //更新供货为已接单
            PbsSupply req = new PbsSupply();
            req.setRecNo(order.getReqId());
            pbsSupplyService.recived(req);
        }
        return save(order);
    }
    /**
     * 
     * 确认订单
     * @author Fandy Liu
     * @created 2018年10月25日 下午10:48:24
     * @return
     */
    public Integer confirmOrder(){
        RequireOrder order = params().entity(RequireOrder.class);
        order = queryOne(order);
        order.setOrderStatus(ORDER_STATUS_DEALING);
        order.setConfirmTime(new Date());//订单确认时间
        Integer orderType = order.getOrderType();
        if(orderType ==1){
            //更新需求为已接单
            PbsRequire req = new PbsRequire();
            req.setRecNo(order.getReqId());
            pbsRequireService.recived(req);
        }
        if(orderType ==2){
            //更新需求为已接单
            PbsSupply req = new PbsSupply();
            req.setRecNo(order.getReqId());
            pbsSupplyService.recived(req);
        }
        return updateSelective(order);//更新订单处理中
    }
    /**
     * 完成订单
     * @author Fandy Liu
     * @created 2018年10月25日 下午11:09:25
     * @return
     */
    public Integer finishOrder(){
        RequireOrder order = params().entity(RequireOrder.class);
        order = queryOne(order);
        order.setOrderStatus(ORDER_STATUS_FINISHED);
        Integer orderType = order.getOrderType();
        if(orderType ==1){
            //更新需求为已完成
            PbsRequire req = new PbsRequire();
            req.setRecNo(order.getReqId());
            pbsRequireService.finish(req);
        }
        if(orderType ==2){
            PbsSupply req = new PbsSupply();
            req.setRecNo(order.getReqId());
            pbsSupplyService.finish(req);
        }
        return updateSelective(order);//更新订单处理中
    }
    /**
     * 取消订单
     * @return
     */
    public Integer cancleOrder(){
        RequireOrder order = params().entity(RequireOrder.class);
        order = queryOne(order);
        order.setOrderStatus(ORDER_STATUS_CANCLED);
        Integer orderType = order.getOrderType();
        if(orderType ==1){
            //更新需求为已完成
            PbsRequire req = new PbsRequire();
            req.setRecNo(order.getReqId());
            pbsRequireService.noRecived(req);//更改需求为未接单
            pbsRequireService.unFinish(req);//更改需求未完成
        }
        if(orderType ==2){
            PbsSupply req = new PbsSupply();
            req.setRecNo(order.getReqId());
            pbsSupplyService.noRecived(req);//更改需求为未接单
            pbsSupplyService.unFinish(req);//更改需求未完成
        }
        return updateSelective(order);//更新订单处理中
    }
    
    /**
     * 描述 生成订单号
     * @author Fandy Liu
     * @created 2018年10月25日 下午10:37:21
     * @return
     */
    private String getOrderIdByUUId() {
        Date date=new Date();
        DateFormat format = new SimpleDateFormat("yyyyMMdd");
        String time = format.format(date);
        int hashCodeV = UUID.randomUUID().toString().hashCode();
        if (hashCodeV < 0) {//有可能是负数
            hashCodeV = -hashCodeV;
        }
        // 0 代表前面补充0
        // 4 代表长度为4
        // d 代表参数为正数型
        return time + String.format("%011d", hashCodeV);
    }

}
