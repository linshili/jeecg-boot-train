package cn.edu.tit.training.service;



import com.baomidou.mybatisplus.extension.service.IService;
import cn.edu.tit.training.entity.TraRecord;
import org.activiti.engine.task.Task;


import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description: 培训记录
 * @Author: jeecg-boot
 * @Date: 2020-04-12
 * @Version: V1.0
 */


public interface ITraRecordService extends IService<TraRecord> {

     void  startProcess4Tra(TraRecord traRecord,HttpServletRequest request);
     List<org.activiti.engine.task.Task> queryTaskALL(HttpServletRequest request);
     void completeTask4Next(TraRecord traRecord ,List<Task> taskList,String currentName);
     void completeTask4pre(TraRecord traRecord,List<Task> taskList,HttpServletRequest request);
     List<TraRecord> getCheckedTra(Integer start,Integer end,TraRecord traRecord);
}
