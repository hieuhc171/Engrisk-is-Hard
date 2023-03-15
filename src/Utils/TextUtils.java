/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Utils;

import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.text.*;

/**
 *
 * @author chieu
 */
public class TextUtils {
    public static void AppendToPane(JTextPane tp, String msg, Color c, int fontSize)
    {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Arial");
        aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);
        aset = sc.addAttribute(aset, StyleConstants.FontSize, fontSize);

        int len = tp.getDocument().getLength();
        tp.setCaretPosition(len);
        tp.setCharacterAttributes(aset, false);
        tp.replaceSelection(msg);
    }

    public static void ReplaceText(JTextPane tp, String oldLine, String newLine) {
        Document doc = tp.getDocument();
        try {
            for (int pos = 0; pos < doc.getLength() - oldLine.length(); pos++) {

                String text = doc.getText(pos, oldLine.length());
                if (oldLine.equals(text)) {
                    doc.remove(pos, oldLine.length());
                    doc.insertString(pos, newLine, null);
                }
            }
        } catch (BadLocationException exp) {
            exp.printStackTrace();
        }
    }
}
