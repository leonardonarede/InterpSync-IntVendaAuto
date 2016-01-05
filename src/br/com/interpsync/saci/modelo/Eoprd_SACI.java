/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.interpsync.saci.modelo;

/**
 *
 * @author Leonardo
 */
public class Eoprd_SACI {
    private int storeno;
    private int ordno;
    private String prdno;
    private String grade;
    private int qtty;
    private int price;
    private int empno;
    private String memo;

    public int getStoreno() {
        return storeno;
    }

    public void setStoreno(int storeno) {
        this.storeno = storeno;
    }

    public int getOrdno() {
        return ordno;
    }

    public void setOrdno(int ordno) {
        this.ordno = ordno;
    }

    public String getPrdno() {
        return prdno;
    }

    public void setPrdno(String prdno) {
        this.prdno = prdno;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public int getQtty() {
        return qtty;
    }

    public void setQtty(int qtty) {
        this.qtty = qtty;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getEmpno() {
        return empno;
    }

    public void setEmpno(int empno) {
        this.empno = empno;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }
    
}
