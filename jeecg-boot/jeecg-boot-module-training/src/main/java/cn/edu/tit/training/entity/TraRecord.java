package cn.edu.tit.training.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.jeecg.common.aspect.annotation.Dict;
import org.jeecgframework.poi.excel.annotation.Excel;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * @Description: 培训记录
 * @Author: jeecg-boot
 * @Date: 2020-04-12
 * @Version: V1.0
 */
@Data
@TableName("tra_record")
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = false)
@ApiModel(value = "tra_record对象", description = "培训记录")
public class TraRecord implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */
    @TableId(type = IdType.ID_WORKER_STR)
    @ApiModelProperty(value = "主键")
    private String id;
    /**
     * 创建人
     */
    @Excel(name = "创建人", width = 15)
    @ApiModelProperty(value = "创建人")
    private String userId;

    /**
     * 开始日期
     */
    @Excel(name = "开始日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "开始日期")
    private Date startTime;
    /**
     * 结束日期
     */
    @Excel(name = "结束日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "结束日期")
    private Date endTime;

    /**
     * 培训地点
     */
    @Excel(name = "培训地点", width = 15)
    @ApiModelProperty(value = "培训地点")
    private String place;
    /**
     * 培训主题
     */
    @Excel(name = "培训主题", width = 15)
    @ApiModelProperty(value = "培训主题")
    private String title;
    /**
     * 培训内容
     */
    @Excel(name = "培训内容", width = 30)
    @ApiModelProperty(value = "培训内容")
    private String content;


    /**
     * 通告对象类型（USER:指定用户，ALL:全体用户）
     */
    @Excel(name = "通告对象类型", width = 15, dicCode = "msg_type")
    @Dict(dicCode = "msg_type")
    private java.lang.String msgType;

    /**
     * 指定用户
     **/
    private java.lang.String userIds;



    /**
     * 培训方式
     */
    @Excel(name = "培训方式", width = 15, dicCode = "tra_type")
    @Dict(dicCode = "tra_type")
    private java.lang.String traType;


    /**
     * 发布状态（0未发布，1已发布，2已撤销 3审核通过  4驳回）
     */
    @Excel(name = "发布状态", width = 15, dicCode = "send_status")
    @Dict(dicCode = "send_status")
    private String sendStatus;



    /**
     * 撤销时间
     */
    @Excel(name = "撤销时间", width = 15, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date cancelTime;



    /**
     * 创建日期
     */
    @Excel(name = "结束日期", width = 20, format = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @ApiModelProperty(value = "创建日期")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd HH:mm:ss")
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private java.util.Date updateTime;


    /**
     * 是否已审批过标记
     * 未审批过 0
     * 已审批过 1
     */
    @Excel(name = "审批标志", width = 15)
    @ApiModelProperty(value = "审批标志")
    private Integer checkFlag;


}
