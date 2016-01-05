/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.interpsync.saci.dao;

import br.com.interpsync.saci.jdbc.ConnectionFactorySACI;
import br.com.interpsync.saci.modelo.Eoprd_SACI;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Leonardo
 */
public class Eoprd_SACIDAO {
    private Connection conexao;
    public Eoprd_SACIDAO(){
/*  20:    */     try
/*  21:    */     {
/*  22: 27 */       this.conexao = ConnectionFactorySACI.getInstance().obterConexao();
/*  23:    */     }
/*  24:    */     catch (SQLException ex)
/*  25:    */     {
/*  26: 29 */       Logger.getLogger(Eoprd_SACIDAO.class.getName()).log(Level.SEVERE, null, ex);
/*  27:    */     }
/*  28:    */     catch (ClassNotFoundException ex)
/*  29:    */     {
/*  30: 31 */       Logger.getLogger(Eoprd_SACIDAO.class.getName()).log(Level.SEVERE, null, ex);
/*  31:    */     }
/*  32:    */     catch (Exception ex)
/*  33:    */     {
/*  34: 33 */       Logger.getLogger(Eoprd_SACIDAO.class.getName()).log(Level.SEVERE, null, ex);
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

                public void inserir(Eoprd_SACI eoprd) 
                        throws SQLException, 
                        Exception{
                   /* String sql = "insert into eoprd (storeno,ordno,prdno,grade,qtty,price,empno,memo) "
                            + " values (?,?,?,?,?,?,?,?)";*/
                    
                    String sql = "INSERT INTO eoprd (ordno,qtty,price,seqno,auxLong1,auxLong2,auxLong3,auxLong4,auxMy1, "
                            + "auxMy2,l1,l2,l3,l4,m1,m2,m3,m4,storeno,empno,status,bits,padbyte, "
                            + "reserved,bits2,bits3,bits4,bits5,s1,s2,s3,s4,prdno,grade,memo,auxString, "
                            + "c1,c2,wshash) VALUES (?,?,?,0,0,0, "
                            + "0,0,0,0,0,0,0,0,0,0, "
                            + "0,0,?,?,2,0,0,0,0, "
                            + "0,0,0,0,0,0,0,?,?,?, "
                            + "0,0,0,0) ";
                    PreparedStatement stmt = getConnection().prepareStatement(sql);
                    stmt.setInt(1, eoprd.getOrdno());
                    stmt.setInt(2, eoprd.getQtty());
                    stmt.setInt(3, eoprd.getPrice());
                    stmt.setInt(4, eoprd.getStoreno());
                    stmt.setInt(5, eoprd.getEmpno());
                    stmt.setString(6, eoprd.getPrdno());
                    stmt.setString(7, eoprd.getGrade());
                    stmt.setString(8, eoprd.getMemo());
                    stmt.executeUpdate();
                    stmt.close();
                    //conexao.close();
                }

    
}
