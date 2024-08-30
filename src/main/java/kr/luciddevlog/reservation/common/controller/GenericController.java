package kr.luciddevlog.reservation.common.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.ArrayList;
import java.util.List;

public abstract class GenericController<D,P,S> {
    // D: DTO, P: primary key, S: service
    S service;
    @RequestMapping(method= RequestMethod.POST)
    public int add(D dto) {
        System.out.println("DTO 객체에 대한 DAO 삽입로직");
        return 0;
    }
    @RequestMapping(method=RequestMethod.PUT)
    public int update(D dto) {
        System.out.println("DTO 객체에 대한 DAO 수정로직");
        return 1;
    }
    @RequestMapping(value="{id}",method=RequestMethod.GET)
    public D getByID(P primaryKey) {
        System.out.println("id 값을 이용하여 DTO 객체를 가져오는 DAO 로직");
        D d=(D) new Object();
        return d;
    }
    @RequestMapping(method=RequestMethod.DELETE)
    public int delete(P primaryKey) {
        System.out.println("id 값을 이용하여 해당 DTO 객체를 삭제하는 DAO 로직");
        return 1;
    }
    @RequestMapping(value="/all",method=RequestMethod.GET)
    public List<D> getByList(){
        System.out.println("DAO에서 DTO 타입의 객체 리스트를 가져오는 로직");
        List<D> dList=new ArrayList();
        return dList;
    }
}
