package com.hetao.demo.entity;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@JsonIgnoreProperties(value={"answerA","answerB","answerC","answerD","answerE"})
@Data
@ApiModel(description = "题目列表")
public class WechatQuestion implements Serializable {

    private static final long serialVersionUID = 2887024841073113760L;

    @ApiModelProperty(value = "自增ID",example = "123")
    private Integer Id;
    @ApiModelProperty(value = "第几天",example = "第n天")
    private String dayOrder;
    @ApiModelProperty("题目编号")
    private Integer QuestionId;
    @ApiModelProperty("问题题目")
	private String questionSubject;
    @ApiModelProperty("备选答案A")
	private String answerA;
    @ApiModelProperty("备选答案B")
	private String answerB;
    @ApiModelProperty("备选答案C")
	private String answerC;
    @ApiModelProperty("备选答案D")
	private String answerD;
    @ApiModelProperty("备选答案E")
	private String answerE;
    @ApiModelProperty("题目答案")
	private String questionAnswer;
    @ApiModelProperty("题目解析")
	private String analysis;
    @ApiModelProperty("专业")
    private String productType;
    @ApiModelProperty("题目类别：1，免费，2，分享，3，付费")
    private Integer payCategory;


	public Integer getId() { return Id; }
	public void setId(Integer id) { Id = id; }
	public String getQuestionSubject() {
		return questionSubject;
	}
	public void setQuestionSubject(String questionSubject) {
		this.questionSubject = questionSubject;
	}
	public String getAnswerA() {
		return answerA;
	}
	public void setAnswerA(String answerA) {
		this.answerA = answerA;
	}
	public String getAnswerB() {
		return answerB;
	}
	public void setAnswerB(String answerB) {
		this.answerB = answerB;
	}
	public String getAnswerC() {
		return answerC;
	}
	public void setAnswerC(String answerC) {
		this.answerC = answerC;
	}
	public String getAnswerD() {
		return answerD;
	}
	public void setAnswerD(String answerD) {
		this.answerD = answerD;
	}
	public String getAnswerE() {
		return answerE;
	}
	public void setAnswerE(String answerE) {
		this.answerE = answerE;
	}
	public String getQuestionAnswer() {
		return questionAnswer;
	}
	public void setQuestionAnswer(String questionAnswer) {
		this.questionAnswer = questionAnswer;
	}
	public String getAnalysis() {
		return analysis;
	}
	public void setAnalysis(String analysis) {
		this.analysis = analysis;
	}
	public String getDayOrder() { return dayOrder; }
	public void setDayOrder(String dayOrder) { this.dayOrder = dayOrder; }
	public Integer getQuestionId() { return QuestionId; }
	public void setQuestionId(Integer questionId) { QuestionId = questionId; }
	public String getProductType() { return productType; }
	public void setProductType(String productType) { this.productType = productType; }
	public Integer getPayCategory() { return payCategory; }
	public void setPayCategory(Integer payCategory) { this.payCategory = payCategory; }

	public List<Map<String, String>> getExamAnswer() {
		List<Map<String, String>> temp = new ArrayList<Map<String, String>>();
		Map<String, String> mapA = new HashMap<String, String>();
		Map<String, String> mapB = new HashMap<String, String>();
		Map<String, String> mapC = new HashMap<String, String>();
		Map<String, String> mapD = new HashMap<String, String>();
		Map<String, String> mapE = new HashMap<String, String>();

		mapA.put("name", "A");
		mapA.put("value", getAnswerA());
		mapB.put("name", "B");
		mapB.put("value", getAnswerB());
		mapC.put("name", "C");
		mapC.put("value", getAnswerC());
		mapD.put("name", "D");
		mapD.put("value", getAnswerD());
		mapE.put("name", "E");
		mapE.put("value", getAnswerE());

		temp.add(mapA);
		temp.add(mapB);
		temp.add(mapC);
		temp.add(mapD);
		temp.add(mapE);

		return temp;
	}

	

}
