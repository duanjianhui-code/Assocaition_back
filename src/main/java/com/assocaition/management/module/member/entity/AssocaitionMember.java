package com.assocaition.management.module.member.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**0-
 * @author Duanjianhui
 * @date 2021-04-24 11:15 上午
 * @describe
 */
@Data
public class AssocaitionMember implements Serializable {
    //id
    private String id;
    //姓名
    private String memberName;
    //部门ID
    private String assId;
    //部门名称
    private String assName;
    //职务
    private String position;
    //成员状态：0--申请中，1--申请通过（预选成员），2--申请通过（正式成员），3--申请不通过
    private String status;
    //成员邮件
    private String memberEmail;
    //成员电话
    private String memberPhone;
    //成员籍贯
    private String nativePlace;
    //成员年龄
    private String memberAge;
    //成员性别
    private String memberSex;
    //用户ID
    private Integer userId;
    //审核意见
    private String opinion;
    //订单号
    private String orderNo;
    //是否付款
    private String isOrder;
    //创建时间
    private Date createTime;

}
