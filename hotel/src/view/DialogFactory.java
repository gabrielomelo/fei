/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author Gabriel Melo
 */
public class DialogFactory {
    
    /**
     * Função estática que retorna um dialogo para notificar o usuário
     * @param context JFrame dono da mensagem
     * @param message Mensagem desejada.
     * @return
     */
    public static JDialog getDialog(JFrame context, String message) {
        JDialog dialog = new JDialog(context, message);
        dialog.add(new JLabel(message));
        dialog.setSize(400, 100);
        return dialog;
    }
    
}
