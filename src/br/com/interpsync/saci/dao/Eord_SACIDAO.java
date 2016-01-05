/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interpsync.saci.dao;

import br.com.interpsync.saci.jdbc.ConnectionFactorySACI;
import br.com.interpsync.saci.modelo.Eord_SACI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo
 */
public class Eord_SACIDAO {
    private Connection conexao;
    public Eord_SACIDAO(){
/*  20:    */     try
/*  21:    */     {
/*  22: 27 */       this.conexao = ConnectionFactorySACI.getInstance().obterConexao();
/*  23:    */     }
/*  24:    */     catch (SQLException ex)
/*  25:    */     {
/*  26: 29 */       Logger.getLogger(Eord_SACIDAO.class.getName()).log(Level.SEVERE, null, ex);
/*  27:    */     }
/*  28:    */     catch (ClassNotFoundException ex)
/*  29:    */     {
/*  30: 31 */       Logger.getLogger(Eord_SACIDAO.class.getName()).log(Level.SEVERE, null, ex);
/*  31:    */     }
/*  32:    */     catch (Exception ex)
/*  33:    */     {
/*  34: 33 */       Logger.getLogger(Eord_SACIDAO.class.getName()).log(Level.SEVERE, null, ex);
/*  35:    */     }
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Connection getConnection()
/*  39:    */     throws SQLException, ClassNotFoundException, Exception
/*  40:    */   {
/*  41: 41 */     if ((this.conexao == null) || (this.conexao.isClosed())) {
/*  42: 42 */       this.conexao = ConnectionFactorySACI.getInstance().obterConexao();
/*  43:    */     }
/*  44: 44 */     return this.conexao;
/*  45:    */   }
/*  46:    */   
              public void inserir(Eord_SACI eord) 
                      throws SQLException, 
                      Exception{
                  /*String sql = "insert into eord (storeno,pdvno,ordno,date,empno,status,custno,paymno,amount,discount,userno,mult) "
                          + " values (?,?,?,DATE_FORMAT(CURDATE(),'%Y%m%d'),?,?,?,?,?,?,?,?)";*/
                  String sql = "INSERT INTO eord(ordno,date,expdate,custno,discount,amount,nfno,other,nfno_futura,mult,auxLong1, "
                          + "auxLong2,auxLong3,auxLong4,auxLong5,auxLong6,auxLong7,auxLong8,auxLong9,auxLong10,auxMy1,auxMy2,auxMy3, "
                          + "auxMy4,auxMy5,auxMy6,auxMy7,auxMy8,auxMy9,auxMy10,dataEntrega,dataMontagem,preVendaNo,preVendaDate, "
                          + "l1,l2,l3,l4,l5,l6,l7,l8,m1,m2,m3,m4,m5,m6,m7,m8,empno,storeno,status,paymno,pdvno, "
                          + "custno_addno,printerOption,userno,bits,padbyte,bits2,bits3,bits4,bits5,bits6,bits7,bits8,bits9, "
                          + "auxShort1,auxShort2,auxShort3,auxShort4,s1,s2,s3,s4,s5,s6,s7,s8,cust_eord,nfse,nfse_futura, "
                          + "auxString,auxString2,auxString3,auxString4,rmkEntrega,rmkMontagem,c1,c2,wshash) VALUES "
                          + "(?,DATE_FORMAT(CURDATE(),'%Y%m%d'),'0',?,?,?,'0','0','0', "
                          + "?,'0','0','0','0','0','0','0', "
                          + "'0','0',?,'0','0','0','0','0','0',  "
                          + "'0','0','0','0','0','0','0','0', "
                          + "'0','0','0','0','0','0','0','0','0','0','0','0','0','0','0', "
                          + "'0',?,?,?,?,?,'0','0',?, "
                          + "'0','0','0','0','0','0','0','0','0','0', "
                          + "'0','0','0','0','0','0','0','0','0','0','0', "
                          + "'0','0','0','0','0','0','0','0', "
                          + "'0','0','0','0','0') ";
                  PreparedStatement stmt = getConnection().prepareStatement(sql);
                  stmt.setInt(1, eord.getOrdno());
                  stmt.setInt(2, eord.getCustno());
                  stmt.setInt(3, eord.getDiscount());
                  stmt.setInt(4, eord.getAmount());
                  stmt.setInt(5, eord.getMult());
                  stmt.setInt(6, eord.getOrdno());
                  stmt.setInt(7, eord.getEmpno());
                  stmt.setInt(8, eord.getStoreno());
                  stmt.setInt(9, eord.getStatus());
                  stmt.setInt(10, eord.getPaymno());
                  stmt.setInt(11, eord.getPdvno());
                  stmt.setInt(12, eord.getUserno());
                  stmt.executeUpdate();
                  stmt.close();
                  //conexao.close();
              }
              
              public Integer proxPed(Integer loja) 
                      throws ClassNotFoundException, 
                      SQLException, 
                      Exception{
                  Integer pedido = 0;
                  String sql = "select (max(ordno) + 1) as ordno from eord where storeno = ? ";
                  PreparedStatement stmt = getConnection().prepareStatement(sql);
                  stmt.setInt(1, loja);
                  stmt.executeQuery();
                  ResultSet rs = stmt.getResultSet();
                  if(rs.next()){
                      pedido = rs.getInt("ordno");
                  }
                  stmt.close();
                  //conexao.close();
                  return pedido;
              }
              
              
}
