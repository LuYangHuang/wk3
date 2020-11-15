package com.cykj.controller;

import com.cykj.pojo.TBaby;
import com.cykj.pojo.THealthcare;
import com.cykj.pojo.TParam;
import com.cykj.service.HealthService;
import com.google.gson.Gson;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/health")
public class HealthController {
    @Resource
    private HealthService healthServiceImpl;


    @RequestMapping("/findBabyHealth")
    public @ResponseBody
    String getFindBabyHealth(int page, int limit) {
        //添加搜索条件
        Map<String, Object> condition = new HashMap<>();
        RowBounds rb = new RowBounds((page - 1) * limit, limit);
        String data = healthServiceImpl.findBabyHealth(rb, condition);
        return data;
    }

    @RequestMapping("/updateBaby")
    public @ResponseBody
    String getUpdateBaby(int id, int babyId, String babyStr, double height, double weigh, double eyesight, double temperature, int situation) {
        System.out.println(id + "--" + babyId + "--" + babyStr + "--" + height + "--" + weigh + "--" + eyesight + "--" + temperature + "--" + situation);
        THealthcare tHealthcare = new THealthcare();
        tHealthcare.setId(id);
        tHealthcare.setBabyId(babyId);
        tHealthcare.setBabyStr(babyStr);
        tHealthcare.setHeight(height);
        tHealthcare.setWeigh(weigh);
        tHealthcare.setEyesight(eyesight);
        tHealthcare.setTemperature(temperature);
        tHealthcare.setSituation(situation);
        int n = healthServiceImpl.updateBaby(tHealthcare);
        int y = healthServiceImpl.updateBabyName(tHealthcare);
        String msg = null;
        if (n > 0 && y > 0) {
            msg = "success";
        } else {
            msg = "fail";
        }
        return msg;

    }

    @RequestMapping("/selectBabyByName")
    public @ResponseBody
    String getSelectBabyByName(String name) {
        System.out.println(name);
        List<TBaby> babyList = healthServiceImpl.selectBaby(name);
        String msg = null;
        System.out.println("babyList:" + babyList.size());
        if (babyList.size() == 0) {
            System.out.println("查询为空");
            msg = "babyNull";
        } else if (babyList.size() > 0) {
            msg = new Gson().toJson(babyList);
        }
        return msg;
    }

    @RequestMapping("/insertBaby")
    public @ResponseBody
    String getInsertBaby(int babyId, String babyStr, double height, double weigh, double eyesight, double temperature, int situation) {
        System.out.println(babyId + "--" + babyStr + "--" + height + "--" + weigh + "--" + eyesight + "--" + temperature + "--" + situation);
        THealthcare tHealthcare = new THealthcare();
        tHealthcare.setBabyStr(babyStr);
        tHealthcare.setHeight(height);
        tHealthcare.setWeigh(weigh);
        tHealthcare.setEyesight(eyesight);
        tHealthcare.setTemperature(temperature);
        tHealthcare.setSituation(situation);
        //添加宝宝的健康信息，前提宝宝存在

        String msg = null;
        // 判断宝宝在健康表中是否已经存在
        THealthcare selectBaby = healthServiceImpl.selectBabyInThealcare(babyId);
        if (selectBaby != null) {
            System.out.println(selectBaby);
            msg = "babyExit";
        } else {
            //健康表中国添加数据
            tHealthcare.setBabyId(babyId);
            int n = healthServiceImpl.insertBaby(tHealthcare);
            if (n > 0) {
                msg = "success";
            } else {
                msg = "fail";
            }
        }
        return msg;
    }


    @RequestMapping("/situationSelect")
    public @ResponseBody
    String getSituationSelect() {
        //查询参数表的健康状态
        System.out.println("WWW");
        List<TParam> list = healthServiceImpl.situationSelect();
        String msg = new Gson().toJson(list);
        return msg;
    }

}
