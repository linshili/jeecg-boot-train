package cn.edu.tit.training.service.impl;



import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.edu.tit.training.entity.TraRecord;
import cn.edu.tit.training.mapper.TraRecordMapper;
import cn.edu.tit.training.service.ITraRecordService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.jeecg.common.exception.JeecgBootException;
import org.jeecg.common.system.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 培训记录
 * @Author: jeecg-boot
 * @Date: 2020-04-12
 * @Version: V1.0
 */


@Service
public class TraRecordServiceImpl extends ServiceImpl<TraRecordMapper, TraRecord> implements ITraRecordService {



    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private TraRecordMapper traRecordMapper;

    @Override
    public void  startProcess4Tra(TraRecord traRecord,HttpServletRequest request) {
        String currentUserName = JwtUtil.getUserNameByToken(request);
        try {

            RuntimeService runtimeService = processEngine.getRuntimeService();
            Map<String,Object> map = new HashMap<>();
            map.put("teacherUserId",currentUserName);
            map.put("flag",true);
            //runtimeService.startProcessInstanceByKey("declaration_process",map);
            String businessKey = traRecord.getId();
            System.out.println("businessKey="+businessKey);
            runtimeService.startProcessInstanceByKey("DeclarationProcess",businessKey,map);
        } catch (JeecgBootException e) {
            e.printStackTrace();
        }

    }

    @Override
    public List<org.activiti.engine.task.Task> queryTaskALL(HttpServletRequest request) {
        String currentUserName = JwtUtil.getUserNameByToken(request);
        TaskService taskService = processEngine.getTaskService();
        return taskService.createTaskQuery().list();
    }



    @Override
    public void completeTask4Next(TraRecord traRecord ,List<Task> taskList,String currentName) {
        //2.通过流程引擎对象获取任务service
        TaskService service = processEngine.getTaskService();
        //3.基于任务ID完成任务
        //如何做出审核，退回 or 通过 ？ 默认按照先画谁走谁  在bpmn文件中如果先画退回，再画通过 则执行时执行退回
        Map<String,Object> map = new HashMap<>();
        map.put("flag",true);
        map.put("adminUserId",currentName);
        map.put("teacherUserId",currentName);
        String instanceID = traRecordMapper.getInstanceIDByBusinessKey(traRecord.getId());
        for(Task task : taskList){
            if(task.getProcessInstanceId().equals(instanceID)){
                service.complete(task.getId(),map);
                System.out.println(task.getId()+"当前任务节点已经完成，将进入下一个任务节点");
            }

        }


    }



    @Override
    public void completeTask4pre(TraRecord traRecord,List<Task> taskList,HttpServletRequest request) {
        String currentUserName = JwtUtil.getUserNameByToken(request);
        //2.通过流程引擎对象获取任务service
        TaskService service = processEngine.getTaskService();
        //3.基于任务ID完成任务
        //如何做出审核，退回 or 通过 ？ 默认按照先画谁走谁  在bpmn文件中如果先画退回，再画通过 则执行时执行退回
        Map<String,Object> map = new HashMap<>();
        map.put("flag",false);
        map.put("adminUserId",currentUserName);
        map.put("teacherUserId",currentUserName);
        String instanceID = traRecordMapper.getInstanceIDByBusinessKey(traRecord.getId());
        for(Task task : taskList){
            if(task.getProcessInstanceId().equals(instanceID)){
                service.complete(task.getId(),map);
                System.out.println(task.getId()+"当前任务节点已经完成，将进入上一个任务节点");
            }

        }
    }

    @Override
    public List<TraRecord> getCheckedTra(Integer start, Integer end, TraRecord traRecord) {
        return traRecordMapper.getCheckedTra(start,end,traRecord);
    }
}
