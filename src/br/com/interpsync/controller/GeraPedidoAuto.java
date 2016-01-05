/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.interpsync.controller;

import br.com.interpsync.interdb.dao.Desreforma_INTERDBDAO;
import br.com.interpsync.interdb.dao.OS_INTERDBDAO;
import br.com.interpsync.interdb.dao.Osvr_INTERDBDAO;
import br.com.interpsync.retreadsystem.dao.OS_ERPDAO;
import br.com.interpsync.retreadsystem.dao.Osvr_ERPDAO;
import br.com.interpsync.retreadsystem.modelo.Desreforma_ERP;
import br.com.interpsync.retreadsystem.modelo.Lotes_ERP;
import br.com.interpsync.retreadsystem.modelo.OS_ERP;
import br.com.interpsync.retreadsystem.modelo.Osvr_ERP;
import br.com.interpsync.saci.dao.Cliente_SACIDAO;
import br.com.interpsync.saci.dao.Eoprd_SACIDAO;
import br.com.interpsync.saci.dao.Eord_SACIDAO;
import br.com.interpsync.saci.dao.Prd_SACIDAO;
import br.com.interpsync.saci.modelo.Cliente_SACI;
import br.com.interpsync.saci.modelo.Eoprd_SACI;
import br.com.interpsync.saci.modelo.Eord_SACI;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.mail.Email;
import org.apache.commons.mail.SimpleEmail;

/**
 *
 * @author Leonardo
 */
public class GeraPedidoAuto {
    private String lojaSACI;
    private String saida = "vazio";
    private List<Eord_SACI> pedidos = new ArrayList<Eord_SACI>();
    private List<Eoprd_SACI> servicosPedidos = new ArrayList<Eoprd_SACI>();
    List<OS_ERP> osconcl = new ArrayList<>();
    private String hostEmail = "";
    private String portEmail = "";
    private String userEmail = "";
    private String passEmail = "";
    private String fromEmail = "";
    private String toEmail = "";
    
    public void gerarPedido(){
        /*Inicio - Buscar Loja da Configuracao*/
        try {
            String local = new File("./bdconfig.txt").getCanonicalFile().toString();
            File arq = new File(local);
            boolean existe = arq.exists();        
            if (existe){
                FileReader fr = new FileReader( arq );
                BufferedReader br = new BufferedReader( fr );
                while(br.ready()){
                    String linha = br.readLine();
                    if(linha.contains("SACILoja:")){
                         lojaSACI = linha.replace("SACILoja:", "").replace(" ", "");
                    }
                }         
            } 
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeraPedidoAuto.class.getName()).log(Level.SEVERE, null, ex);
            StackTraceElement st[] = ex.getStackTrace();
            String erro = "";
            for (int i = 0;i < st.length;i++){
                erro += st[i].toString() + "\n";
            } 
            new Geralog().enviar("IntVenda", erro);             
        } catch (IOException ex) {
            Logger.getLogger(GeraPedidoAuto.class.getName()).log(Level.SEVERE, null, ex);
            StackTraceElement st[] = ex.getStackTrace();
            String erro2 = "";
            for (int i = 0;i < st.length;i++){
                erro2 += st[i].toString() + "\n";
            } 
            new Geralog().enviar("IntVenda", erro2);
        }
        /*Fim -  - Buscar Loja da Configuracao*/   
        
        /*Inicio - Pegar Configurações de email do arquivo*/
                  try {
                  String local = new File("./email.txt").getCanonicalFile().toString();
                  File arq = new File(local);
                  boolean existe = arq.exists();        
                      if (existe){
                          FileReader fr = new FileReader( arq );
                          BufferedReader br = new BufferedReader( fr );
                          while(br.ready()){
                              String linha = br.readLine();
                              if(linha.contains("host:")){
                                  hostEmail = linha.replace("host:", "").replace(" ", "");
                              }
                              if(linha.contains("port:")){
                                  portEmail = linha.replace("port:", "").replace(" ", "");
                              }
                              if(linha.contains("user:")){
                                  userEmail = linha.replace("user:", "").replace(" ", "");
                              }
                              if(linha.contains("pass:")){
                                  passEmail = linha.replace("pass:", "").replace(" ", "");
                              }
                              if(linha.contains("from:")){
                                  fromEmail = linha.replace("from:", "").replace(" ", "");
                              }
                              if(linha.contains("to:")){
                                  toEmail = linha.replace("to:", "").replace(" ", "");
                              }
                          }
                     
                      } 
                   } catch (FileNotFoundException ex) {
                          Logger.getLogger(GeraPedidoAuto.class.getName()).log(Level.SEVERE, null, ex);
                          StackTraceElement st[] = ex.getStackTrace();
                          String erro = "";
                          for (int i = 0;i < st.length;i++){
                              erro += st[i].toString() + "\n";
                          } 
                          new Geralog().enviar("GeraPedidoAuto", erro);
                   } catch (IOException ex) {
                          Logger.getLogger(GeraPedidoAuto.class.getName()).log(Level.SEVERE, null, ex);
                          StackTraceElement st[] = ex.getStackTrace();
                          String erro2 = "";
                          for (int i = 0;i < st.length;i++){
                              erro2 += st[i].toString() + "\n";
                          } 
                          new Geralog().enviar("GeraPedidoAuto", erro2);
                  }
                  /*FIM - Pegar Configurações de email do arquivo*/
        
        try {
            OS_INTERDBDAO osintdao = new OS_INTERDBDAO();
            OS_ERPDAO oserpdao = new OS_ERPDAO();
            List<Lotes_ERP> listaLote = osintdao.listaLotesPendentesSACI();
            if(listaLote.size() > 0){
                for(Lotes_ERP lote : listaLote){
                    //System.out.println("Lote: "+lote.getNrLote()+" Cliente ID: "+lote.getClienteID());
                    List<OS_ERP> listaOS = oserpdao.listarLote(lote.getClienteID(), lote.getNrLote());
                    for(OS_ERP os1 : listaOS){
                          //System.out.println("OS: "+os.getNrOS());
                          osintdao.atualizar(os1);
                    }
        
                }
            }
            System.out.println("Passou!");
            List<Lotes_ERP> listaLote2 = osintdao.listaLotesPendentesSACI();
            List<Lotes_ERP> LotesConcluidos = new ArrayList<>();
            if(listaLote2.size() > 0){
                for(Lotes_ERP lote : listaLote2){
                    List<OS_ERP> listaOS = oserpdao.listarLote(lote.getClienteID(), lote.getNrLote());
                    for(OS_ERP os : listaOS){
                        /*String data1 = "20010101";
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
                        Date dataf = sdf.parse(data1);*/
                        if(os.getStEI() != 4 && os.getStEI() != 5){
                            lote.setFechado(false);
                        }
                    }
                    if(lote.isFechado()){
                        LotesConcluidos.add(lote);
                    }
                }
                String cpfcgc = "";
                String controle = "";
                Integer pedidoAtual = 0;
                Integer vendedorAtual = 0; 
                int j = -1;
                int i=0;
                int controle2 = 999999999;
                for(Lotes_ERP lotec : LotesConcluidos){
                   if (!cpfcgc.equals(lotec.getClienteID())){
                       j++;
                       cpfcgc = lotec.getClienteID();
                       Cliente_SACIDAO cldao = new Cliente_SACIDAO();
                       Eord_SACI eord = new Eord_SACI();
                       if(cpfcgc.length() == 16){
                           controle = cpfcgc.substring(0, 2)+"."+cpfcgc.substring(2, 5)+"."+cpfcgc.substring(5,16);
                           //System.out.println(cpfcgc);
                       }else{
                           controle = cpfcgc.substring(0, 3)+"."+cpfcgc.substring(3, 6)+"."+cpfcgc.substring(6, 12);
                           //System.out.println(cpfcgc);
                       }
                       Cliente_SACI cliente = cldao.buscarCl(controle);
                       eord.setStoreno(Integer.parseInt(lojaSACI));
                       eord.setEmpno(Integer.parseInt(lotec.getVendedorID()));
                       eord.setCustno(cliente.getNo());
                       eord.setPaymno(30);
                       eord.setDiscount(0);
                       eord.setMult(1);
                       eord.setPdvno(1);
                       eord.setUserno(1);
                       eord.setStatus(2);
                       eord.setAmount(0);
                       Eord_SACIDAO eorddao = new Eord_SACIDAO();
                       eord.setOrdno(eorddao.proxPed(Integer.parseInt(lojaSACI))+j);
                       pedidoAtual = eord.getOrdno();
                       vendedorAtual = eord.getEmpno();
                       if(cldao.isValido(cliente.getNo())){
                           pedidos.add(eord);
                       }
                   }
                   
                   
                   osconcl = osintdao.listarOsPendenteSACI(lotec.getNrLote(), lotec.getClienteID());
                   System.out.println("Lote: "+lotec.getNrLote()+" - "+lotec.getClienteID());
                   
                   
                   for(OS_ERP os : osconcl){
                       //System.out.println("OS: "+os.getNrOS());
                       List<Osvr_ERP> listaConsumo = new Osvr_INTERDBDAO().listarConsumo(os.getNrOS());
                       List<Eoprd_SACI> ListaServ = new ArrayList<>();
                       if(controle2 != pedidoAtual){
                           controle2 = pedidoAtual;
                           i=0;
                       }
                       for(Osvr_ERP osvr : listaConsumo){
                           String prod = "";
                           Eoprd_SACI eoprd = new Eoprd_SACI();
                           Prd_SACIDAO prddao = new Prd_SACIDAO();
                           if("B".equals(osvr.getTipoReg())){
                               String clios = "";
                               if(os.getClienteID().length() == 16){
                                    clios = os.getClienteID().substring(0, 2)+"."+os.getClienteID().substring(2, 5)+"."+os.getClienteID().substring(5,16);
                                    //System.out.println(cpfcgc);
                               }else{
                                    clios = os.getClienteID().substring(0, 3)+"."+os.getClienteID().substring(3, 6)+"."+os.getClienteID().substring(6, 12);
                                    //System.out.println(cpfcgc);
                               }
                               Cliente_SACIDAO clidao = new Cliente_SACIDAO();
                               Cliente_SACI cli = clidao.buscarCl(clios);
                               
                               if(clidao.isValido(cli.getNo())){
                                  i++;
                                  if(i > 9){
                                     eoprd.setGrade(""+i);
                                  }else{
                                     eoprd.setGrade("0"+i);  
                                  }
                                  eoprd.setQtty(1000);
                                  eoprd.setStoreno(Integer.parseInt(lojaSACI));
                                  eoprd.setMemo("OS: "+os.getNrOS()+"/"+os.getMarcaID()+"-"+os.getNrMatricula()+"-"+osvr.getCodigoReg());
                                  Desreforma_INTERDBDAO drefdao = new Desreforma_INTERDBDAO();
                                  Desreforma_ERP desenho = drefdao.localizarPorID(osvr.getCodigoReg());
                                  prod = prddao.localizarServico(desenho.getBlde(), os.getMedidaID());
                                if (prod == null)
                               {
                                    Email email = new SimpleEmail();
                                    email.setHostName(hostEmail);
                                    email.setSmtpPort(Integer.parseInt(portEmail));
                                    email.setAuthentication(userEmail, passEmail);
                                    email.setFrom(fromEmail);
                                    email.setSubject("Erro na Integração SACI x Retread System - GeraPedidoAuto");
                                    email.setMsg("Serviço não foi localizado no SACI: " + osvr.getCodigoReg() + " Tipo: " + osvr.getTipoReg());
                                    email.addTo(toEmail);
                                    email.send();
                                }
                                  eoprd.setPrdno(prod);
                                  eoprd.setPrice(prddao.pegarPreco(prod));
                                  eoprd.setOrdno(pedidoAtual);
                                  eoprd.setEmpno(vendedorAtual);
                                  System.out.println("Pedido: "+eoprd.getOrdno()+" Produto: "+eoprd.getPrdno()+" Grade: "+eoprd.getGrade()+" Detalhes"+eoprd.getMemo());
                                  servicosPedidos.add(eoprd);
                               }
                               
                           }
                           
                           if("R".equals(osvr.getTipoReg()) && "UP8".equals(osvr.getCodigoReg())){
                               Osvr_INTERDBDAO osvrdao = new Osvr_INTERDBDAO();
                               if(!osvrdao.temReforma(osvr.getNrOS())){
                               
                                       String clios = "";
                                       if(os.getClienteID().length() == 16){
                                            clios = os.getClienteID().substring(0, 2)+"."+os.getClienteID().substring(2, 5)+"."+os.getClienteID().substring(5,16);
                                            //System.out.println(cpfcgc);
                                       }else{
                                            clios = os.getClienteID().substring(0, 3)+"."+os.getClienteID().substring(3, 6)+"."+os.getClienteID().substring(6, 12);
                                            //System.out.println(cpfcgc);
                                       }
                                       Cliente_SACIDAO clidao = new Cliente_SACIDAO();
                                       Cliente_SACI cli = clidao.buscarCl(clios);

                                       if(clidao.isValido(cli.getNo())){
                                           i++;
                                           if(i > 9){
                                              eoprd.setGrade(""+i);
                                           }else{
                                              eoprd.setGrade("0"+i);  
                                           }
                                           eoprd.setQtty(1000);
                                           eoprd.setStoreno(Integer.parseInt(lojaSACI));
                                           eoprd.setMemo("OS: "+os.getNrOS()+"/"+os.getMarcaID()+"-"+os.getNrMatricula()+"-"+osvr.getCodigoReg());
                                           //Busca Serviço de Conserto - Codigo Manchao
                                           prod = prddao.localizarConserto("R",osvr.getCodigoReg(),false);
                                           eoprd.setPrdno(prod);
                                           eoprd.setPrice(prddao.pegarPreco(prod));
                                           eoprd.setOrdno(pedidoAtual);
                                           eoprd.setEmpno(vendedorAtual);

                                           servicosPedidos.add(eoprd);
                                       }
                                }
                           }
                           
                           
                           if("R".equals(osvr.getTipoReg()) && !"UP8".equals(osvr.getCodigoReg())){
                               String clios = "";
                               if(os.getClienteID().length() == 16){
                                    clios = os.getClienteID().substring(0, 2)+"."+os.getClienteID().substring(2, 5)+"."+os.getClienteID().substring(5,16);
                                    //System.out.println(cpfcgc);
                               }else{
                                    clios = os.getClienteID().substring(0, 3)+"."+os.getClienteID().substring(3, 6)+"."+os.getClienteID().substring(6, 12);
                                    //System.out.println(cpfcgc);
                               }
                               Cliente_SACIDAO clidao = new Cliente_SACIDAO();
                               Cliente_SACI cli = clidao.buscarCl(clios);
                               
                               if(clidao.isValido(cli.getNo())){
                                   i++;
                                   if(i > 9){
                                      eoprd.setGrade(""+i);
                                   }else{
                                      eoprd.setGrade("0"+i);  
                                   }
                                   eoprd.setQtty(1000);
                                   eoprd.setStoreno(Integer.parseInt(lojaSACI));
                                   eoprd.setMemo("OS: "+os.getNrOS()+"/"+os.getMarcaID()+"-"+os.getNrMatricula()+"-"+osvr.getCodigoReg());
                                   //Busca Serviço de Conserto - Codigo Manchao
                                   if(osvr.getCodigoReg().startsWith("RAD")){
                                       Osvr_INTERDBDAO osvrdao = new Osvr_INTERDBDAO();
                                        if(osvrdao.temReforma(osvr.getNrOS())){
                                             //Com Reforma
                                             prod = prddao.localizarConserto("R",osvr.getCodigoReg(),true);
                                        }else{
                                             //Sem Reforma
                                             prod = prddao.localizarConserto("R",osvr.getCodigoReg(),false);
                                        }
                                    }else{
                                         prod = "            1742";
                                    }
                                   eoprd.setPrdno(prod);
                               
                                   eoprd.setPrice(prddao.pegarPreco(prod));
                                   eoprd.setOrdno(pedidoAtual);
                                   eoprd.setEmpno(vendedorAtual);
                               
                                   servicosPedidos.add(eoprd);
                               }
                           }  
                       }
                   }
                }
            }
            if(pedidos.size() > 0){
                if(servicosPedidos.size() > 0){
                    Eord_SACIDAO eodao = new Eord_SACIDAO();
                    Eoprd_SACIDAO eopdao = new Eoprd_SACIDAO();
                    for(Eord_SACI eordsaci : pedidos){
                        Boolean temprd = false;
                        List<OS_ERP> listaOS = new ArrayList<>();
                        for(Eoprd_SACI eoprdsaci : servicosPedidos){
                            if(eoprdsaci.getOrdno() == eordsaci.getOrdno()){
                                eopdao.inserir(eoprdsaci);
                                temprd = true;
                                int indice = 0;
                                indice = eoprdsaci.getMemo().indexOf("/");
                                Integer numOS = Integer.parseInt(eoprdsaci.getMemo().substring(0, indice).replace("OS: ",""));
                                OS_ERP os = new OS_ERP();
                                os = osintdao.localizarPorID(numOS);
                                os.setStatusERP(2);
                                Boolean temOS = false;
                                for(OS_ERP osIN : listaOS){
                                    if(Objects.equals(osIN.getNrOS(), os.getNrOS())){
                                        temOS = true;
                                    }
                                }
                                if(!temOS){
                                    listaOS.add(os);
                                }
                                
                            }
                        }
                        if(temprd){
                                eodao.inserir(eordsaci);
                                System.out.println("Pedido: "+eordsaci.getOrdno());
                                for(OS_ERP osFim : listaOS){
                                    System.out.println("OS: "+osFim.getNrOS());
                                    osintdao.atualizarStatus(osFim);
                                }
                        }
                    }
                    
                    //for(OS_ERP os : osconcl){
                        //os.setStatusERP(2);
                        //osintdao.atualizar(os);
                    //}
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(GeraPedidoAuto.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(GeraPedidoAuto.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
