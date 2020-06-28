package cn.edu.tit.training.controller;


import cn.edu.tit.training.service.impl.TraRecordServiceImpl;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import lombok.extern.slf4j.Slf4j;
import cn.edu.tit.training.entity.TraRecord;
import cn.edu.tit.training.service.ITraRecordService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.constant.CommonConstant;
import org.jeecg.common.constant.CommonSendStatus;
import org.jeecg.common.system.base.controller.JeecgController;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.system.vo.SysUserCacheInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Description: 培训记录
 * @Author: jeecg-boot
 * @Date: 2020-04-12
 * @Version: V1.0
 */


@Api(tags = "jeecg-boot-module-training")
@RestController
@RequestMapping("/tra/training")
@Slf4j
public class TraRecordController extends JeecgController<TraRecord, ITraRecordService> {

    @Autowired
    private ITraRecordService traRecordService;



    /**
     * 分页列表查询
     *
     * @param traRecord
     * @param pageNo
     * @param pageSize
     * @param req
     * @return
     */


    @AutoLog(value = "培训记录-分页列表查询")
    @ApiOperation(value = "培训记录-分页列表查询", notes = "培训记录-分页列表查询")
    @GetMapping(value = "/list")
    public Result<?> queryPageList(TraRecord traRecord,
                                   @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                   @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                   HttpServletRequest req,HttpServletRequest request) {
        String currentUserName = JwtUtil.getUserNameByToken(request);
        req.setAttribute("user_id",currentUserName);
        QueryWrapper<TraRecord> queryWrapper = QueryGenerator.initQueryWrapper(traRecord, req.getParameterMap());
        Page<TraRecord> page = new Page<TraRecord>(pageNo, pageSize);
        IPage<TraRecord> pageList = traRecordService.page(page, queryWrapper);
        return Result.ok(pageList);
    }


    @AutoLog(value = "培训记录-审核分页列表查询")
    @ApiOperation(value = "培训记录-审核分页列表查询", notes = "培训记录-审核分页列表查询")
    @GetMapping(value = "/queryPageListByCurrUser")
    public Result<?> queryPageListByCurrUser(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req,HttpServletRequest request){
        TraRecord traRecord = new TraRecord();
        traRecord.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);
        String currentUserName = JwtUtil.getUserNameByToken(request);
        req.setAttribute("user_id",currentUserName);
        QueryWrapper<TraRecord> queryWrapper = QueryGenerator.initQueryWrapper(traRecord, req.getParameterMap());
        Page<TraRecord> page = new Page<TraRecord>(pageNo, pageSize);
        IPage<TraRecord> pageList = traRecordService.page(page, queryWrapper);
        return Result.ok(pageList);
    }

    @AutoLog(value = "已审批培训记录")
    @ApiOperation(value = "已审批培训记录", notes = "已审批培训记录")
    @GetMapping(value = "/queryPageListByComp")
    public Result<?> queryPageListByComp(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                             @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                             HttpServletRequest req,HttpServletRequest request){
        TraRecord traRecord = new TraRecord();
        traRecord.setCheckFlag(CommonSendStatus.ChECKED_STATUS_1);
        String currentUserName = JwtUtil.getUserNameByToken(request);
        req.setAttribute("user_id",currentUserName);
        QueryWrapper<TraRecord> queryWrapper = QueryGenerator.initQueryWrapper(traRecord, req.getParameterMap());
        Page<TraRecord> page = new Page<TraRecord>(pageNo, pageSize);
        IPage<TraRecord> pageList = traRecordService.page(page, queryWrapper);
        return Result.ok(pageList);
        /*Integer start = (pageNo - 1)*pageSize;
        Integer end = pageSize;
        TraRecord traRecord = new TraRecord();
        traRecord.setCheckFlag(CommonSendStatus.ChECKED_STATUS_1);
        List<TraRecord> listTra = traRecordService.getCheckedTra(start,end,traRecord);
        return Result.ok(listTra);*/
    }



    /**
     * 添加
     *
     * @param traRecord
     * @return
     */


    @AutoLog(value = "培训记录-添加")
    @ApiOperation(value = "培训记录-添加", notes = "培训记录-添加")
    @PostMapping(value = "/add")
    public Result<?> add(@RequestBody TraRecord traRecord,HttpServletRequest request) {
        Result<TraRecord> result = new Result<TraRecord>();
        String currentUserName = JwtUtil.getUserNameByToken(request);
        try{
            traRecord.setUserId(currentUserName);
            traRecord.setSendStatus(CommonSendStatus.UNPUBLISHED_STATUS_0);//未发布
            traRecordService.save(traRecord);
            //启动流程并完成教师填写申报信息任务
            traRecordService.startProcess4Tra(traRecord,request);
            //查询任务
            List<org.activiti.engine.task.Task> taskList =  traRecordService.queryTaskALL(request);
            //完成教师填写申报信息任务
            traRecordService.completeTask4Next(traRecord ,taskList,currentUserName);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
            result.error500("操作失败");
        }

        return result.ok("添加成功！");
    }

    /**
     * 编辑
     *
     * @param traRecord
     * @return
     */


    @AutoLog(value = "培训记录-编辑")
    @ApiOperation(value = "培训记录-编辑", notes = "培训记录-编辑")
    @PutMapping(value = "/edit")
    public Result<?> edit(@RequestBody TraRecord traRecord) {
        traRecordService.updateById(traRecord);
        return Result.ok("编辑成功!");
    }

    /**
     * 通过id删除
     *
     * @param id
     * @return
     */


    @AutoLog(value = "培训记录-通过id删除")
    @ApiOperation(value = "培训记录-通过id删除", notes = "培训记录-通过id删除")
    @DeleteMapping(value = "/delete")
    public Result<?> delete(@RequestParam(name = "id", required = true) String id) {
        traRecordService.removeById(id);
        return Result.ok("删除成功!");
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */


    @AutoLog(value = "培训记录-批量删除")
    @ApiOperation(value = "培训记录-批量删除", notes = "培训记录-批量删除")
    @DeleteMapping(value = "/deleteBatch")
    public Result<?> deleteBatch(@RequestParam(name = "ids", required = true) String ids) {
        this.traRecordService.removeByIds(Arrays.asList(ids.split(",")));
        return Result.ok("批量删除成功!");
    }

    /**
     * 通过id查询
     *
     * @param id
     * @return
     */


    @AutoLog(value = "培训记录-通过id查询")
    @ApiOperation(value = "培训记录-通过id查询", notes = "培训记录-通过id查询")
    @GetMapping(value = "/queryById")
    public Result<?> queryById(@RequestParam(name = "id", required = true) String id) {
        TraRecord traRecord = traRecordService.getById(id);
        if (traRecord == null) {
            return Result.error("未找到对应数据");
        }
        return Result.ok(traRecord);
    }



    /**
     *	 更新发布操作
     * @param id
     * @return
     */
    @RequestMapping(value = "/doReleaseDataForTra", method = RequestMethod.GET)
    public Result<TraRecord> doReleaseDataForTra(@RequestParam(name="id",required=true) String id, HttpServletRequest request) {
        Result<TraRecord> result = new Result<TraRecord>();
        String currentName = JwtUtil.getUserNameByToken(request);
        TraRecord traRecord = traRecordService.getById(id);
        if(traRecord==null) {
            result.error500("未找到对应实体");
        }else {
            traRecord.setSendStatus(CommonSendStatus.PUBLISHED_STATUS_1);//发布中
            traRecord.setCreateTime(new Date());
            boolean ok = traRecordService.updateById(traRecord);
            //查询任务 一位教师的申请有很多个记录 根据当前教师培训信息id即businessId查找
            // act_ru_exection ID（实例ID）,根据实例ID执行相对应的任务流程
            List<org.activiti.engine.task.Task> taskList =  traRecordService.queryTaskALL(request);
            //完成教师申请任务
            traRecordService.completeTask4Next(traRecord,taskList,currentName);
            if(ok) {
                result.success("该培训申报发布成功");
            }else{
                result.success("该培训申报发布失败");
            }
        }

        return result;
    }


    /**
            *	 更新撤销操作
	 * @param id
	 * @return
             */
    @RequestMapping(value = "/doReovkeDataForTra", method = RequestMethod.GET)
    public Result<TraRecord> doReovkeDataForTra(@RequestParam(name="id",required=true) String id, HttpServletRequest request) {
        Result<TraRecord> result = new Result<TraRecord>();
        TraRecord traRecord = traRecordService.getById(id);
        if(traRecord==null) {
            result.error500("未找到对应实体");
        }else {
            traRecord.setSendStatus(CommonSendStatus.REVOKE_STATUS_2);//撤销发布
            traRecord.setCancelTime(new Date());
            boolean ok = traRecordService.updateById(traRecord);
            //查询任务
            List<org.activiti.engine.task.Task> taskList =  traRecordService.queryTaskALL(request);
            //撤销任务
            traRecordService.completeTask4pre(traRecord,taskList,request);
            if(ok) {
                result.success("该项目申报撤销成功");
            }
        }

        return result;
    }


    /**
     *	 审核通过
     * @param id
     * @return
     */
    @RequestMapping(value = "/checkPassForTra", method = RequestMethod.GET)
    public Result<TraRecord> checkPassForTra (@RequestParam(name="id",required=true) String id, HttpServletRequest request){
        String currentName = JwtUtil.getUserNameByToken(request);
        Result<TraRecord> result = new Result<TraRecord>();
        TraRecord traRecord = traRecordService.getById(id);
        if(traRecord==null) {
            result.error500("未找到对应实体");
        }else {
            traRecord.setSendStatus(CommonSendStatus.CHECKPASS_STATUS_3);//审核通过
            traRecord.setCheckFlag(CommonSendStatus.ChECKED_STATUS_1);
            boolean ok = traRecordService.updateById(traRecord);
            //查询任务 一位教师的申请有很多个记录 根据当前教师培训信息id即businessId查找
            // act_ru_exection ID（实例ID）,根据实例ID执行相对应的任务流程
            List<org.activiti.engine.task.Task> taskList =  traRecordService.queryTaskALL(request);
            //审核通过
            traRecordService.completeTask4Next(traRecord,taskList,currentName);

            if(ok) {
                result.success("该培训申报审核通过");
            }else{
                result.success("该培训申报审核失败");
            }
        }

        return result;
    }


    /**
     *	 审核驳回
     * @param id
     * @return
     */
    @RequestMapping(value = "/unDoForTra", method = RequestMethod.GET)
    public Result<TraRecord> unDoForTra (@RequestParam(name="id",required=true) String id, HttpServletRequest request){
        String currentName = JwtUtil.getUserNameByToken(request);
        Result<TraRecord> result = new Result<TraRecord>();
        TraRecord traRecord = traRecordService.getById(id);
        if(traRecord==null) {
            result.error500("未找到对应实体");
        }else {
            traRecord.setSendStatus(CommonSendStatus.UNPUBLISHED_STATUS_0);//审核驳回
            traRecord.setCheckFlag(CommonSendStatus.ChECKED_STATUS_1);
            boolean ok = traRecordService.updateById(traRecord);
            //查询任务 一位教师的申请有很多个记录 根据当前教师培训信息id即businessId查找
            // act_ru_exection ID（实例ID）,根据实例ID执行相对应的任务流程
            List<org.activiti.engine.task.Task> taskList =  traRecordService.queryTaskALL(request);
            //审核通过
            traRecordService.completeTask4pre(traRecord,taskList,request);

            if(ok) {
                result.success("该培训申报被驳回");
            }else{
                result.success("该培训申报被驳回失败");
            }
        }

        return result;
    }



    /**
     * 导出excel
     *
     * @param request
     * @param traRecord
     */


    @RequestMapping(value = "/exportXls")
    public ModelAndView exportXls(HttpServletRequest request, TraRecord traRecord) {
        return super.exportXls(request, traRecord, TraRecord.class, "培训记录");
    }

    /**
     * 通过excel导入数据
     *
     * @param request
     * @param response
     * @return
     */


    @RequestMapping(value = "/importExcel", method = RequestMethod.POST)
    public Result<?> importExcel(HttpServletRequest request, HttpServletResponse response) {
        return super.importExcel(request, response, TraRecord.class);
    }

}
