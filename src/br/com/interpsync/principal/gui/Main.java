/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.com.interpsync.principal.gui;

import br.com.interpsync.controller.GeraPedidoAuto;
//import javax.swing.JOptionPane;

/**
 *
 * @author Leonardo
 */
public class Main {
    public static void main(String[] args) {
           GeraPedidoAuto geraPed =  new GeraPedidoAuto();
           geraPed.gerarPedido();
           //JOptionPane.showMessageDialog(null, "Executado com sucesso!","Status execução",JOptionPane.INFORMATION_MESSAGE);
    }
}
