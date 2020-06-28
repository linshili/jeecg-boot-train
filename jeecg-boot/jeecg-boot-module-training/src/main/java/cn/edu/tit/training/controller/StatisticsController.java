package cn.edu.tit.training.controller;

import cn.edu.tit.training.entity.TraRecord;
import cn.edu.tit.training.service.IStatisticsService;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.jeecg.common.api.vo.Result;
import org.jeecg.common.aspect.annotation.AutoLog;
import org.jeecg.common.system.query.QueryGenerator;
import org.jeecg.common.system.util.JwtUtil;
import org.jeecg.common.util.oConvertUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Api(tags = "jeecg-boot-module-training")
@RestController
@RequestMapping("/tra/statistics")
@Slf4j
public class StatisticsController {

    @Autowired
    private IStatisticsService statisticsService;


    /**
     *  系部统计查询
     * @param req
     * @param request
     * @return
     */
    @AutoLog(value = "系部统计查询")
    @ApiOperation(value = "系部统计查询", notes = "系部统计查询")
    @GetMapping(value = "/queryByDepartment")
    public Result<?> queryByDepartment(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "10") Integer pageSize,
                                       HttpServletRequest req, HttpServletRequest request) {
        List<Map<Object,Object>> resMap = statisticsService.getStatisticsByDep();
        return Result.ok(resMap);
    }


    /**
     *  个人排行榜
     * @param req
     * @param request
     * @return
     */
    @AutoLog(value = "个人排行榜")
    @ApiOperation(value = "个人排行榜", notes = "个人排行榜")
    @GetMapping(value = "/queryStatisticsByPer")
    public Result<?> queryStatisticsByPer(@RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo,
                                       @RequestParam(name = "pageSize", defaultValue = "7") Integer pageSize,
                                       HttpServletRequest req, HttpServletRequest request) {
        List<Map<Object,Object>> resMap = statisticsService.getStatisticsByPer(pageSize);
        return Result.ok(resMap);
    }


    /**
     *  个人排行榜
     * @param req
     * @param request
     * @return
     */
    @AutoLog(value = "申报统计信息")
    @ApiOperation(value = "申报统计信息", notes = "申报统计信息")
    @GetMapping(value = "/queryDeclarinfo")
    public Result<?> queryDeclarinfo(HttpServletRequest req, HttpServletRequest request) {
        Result<JSONObject> result = new Result<JSONObject>();
        JSONObject obj = new JSONObject();
        //update-begin--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        // 获取一天的开始和结束时间
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Date dayStart = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date dayEnd = calendar.getTime();
        // 获取系统访问记录
        Long findTotalDecCount = statisticsService.findTotalDecCount();
        obj.put("findTotalDecCount", findTotalDecCount);
        Long todayDecCount = statisticsService.todayDecCount(dayStart,dayEnd);
        obj.put("todayDecCount", todayDecCount);
        Long todayUser = statisticsService.findTodayUser(dayStart,dayEnd);
        //update-end--Author:zhangweijian  Date:20190428 for：传入开始时间，结束时间参数
        obj.put("todayUser", todayUser);
        result.setResult(obj);
        result.success("获取信息成功");
        return result;
    }

    /**
     * 获取访问量
     * @return
     */
    @GetMapping("findDecCount")
    public Result<List<Map<String,Object>>> findDecCount() {
        Result<List<Map<String,Object>>> result = new Result<List<Map<String,Object>>>();
        Calendar calendar = new GregorianCalendar();
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        calendar.set(Calendar.MILLISECOND,0);
        calendar.add(Calendar.DAY_OF_MONTH, 1);
        Date dayEnd = calendar.getTime();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        Date dayStart = calendar.getTime();
        List<Map<String,Object>> list = statisticsService.findDecCount(dayStart, dayEnd);
        result.setResult(oConvertUtils.toLowerCasePageList(list));
        return result;
    }


}
