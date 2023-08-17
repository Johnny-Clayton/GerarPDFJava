package br.com.gerarpdf.view;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class ViewGerarPDF2 extends JFrame {

    private JEditorPane previewPane;
    private JButton savePdfButton;

    public ViewGerarPDF2() {
        initComponents();
    }

    private void initComponents() {
        JButton previewButton = new JButton();
        savePdfButton = new JButton();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Gerar PDF");
        setResizable(false);

        previewPane = new JEditorPane();
        previewPane.setContentType("text/plain");
        previewPane.setText("Insira o texto...");

        JScrollPane scrollPane = new JScrollPane(previewPane);

        previewButton.setText("Pré-visualizar");
        previewButton.addActionListener(evt -> previewContent());

        savePdfButton.setText("Salvar PDF");
        savePdfButton.setEnabled(false);
        savePdfButton.addActionListener(evt -> generatePdf());

        var layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                        .addComponent(scrollPane)
                        .addGroup(layout.createSequentialGroup()
                                .addComponent(previewButton)
                                .addGap(300)
                                .addComponent(savePdfButton)))
                .addGap(20));

        layout.setVerticalGroup(layout.createSequentialGroup()
                .addGap(20)
                .addComponent(scrollPane, 600, 600, Short.MAX_VALUE)
                .addGap(20)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                        .addComponent(previewButton)
                        .addComponent(savePdfButton))
                .addGap(20));

        pack();
        setLocationRelativeTo(null);
    }

    private void previewContent() {
        // Aqui, por enquanto, apenas habilitamos o botão "Salvar PDF".
        // Em uma aplicação real, você pode querer renderizar um preview mais próximo do PDF aqui.
        savePdfButton.setEnabled(true);
    }

    private void generatePdf() {
        String filename = "documento.pdf";
        if (createPdf(filename, previewPane.getText())) {
            openFile(filename);
        } else {
            JOptionPane.showMessageDialog(this, "Falha ao gerar PDF.");
        }
    }

    private boolean createPdf(String filename, String content) {
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, new FileOutputStream(filename));
            document.open();
            document.add(new Paragraph(content));
            return true;
        } catch (DocumentException | FileNotFoundException ex) {
            System.err.println("Error creating PDF:");
            ex.printStackTrace();
            return false;
        } finally {
            if (document != null) {
                document.close();
            }
        }
    }

    private void openFile(String filename) {
        try {
            Desktop.getDesktop().open(new File(filename));
        } catch (IOException ex) {
            System.err.println("Error opening file:");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ex) {
            System.err.println("Failed to set look and feel:");
            ex.printStackTrace();
        }

        EventQueue.invokeLater(() -> new ViewGerarPDF().setVisible(true));
    }
}
