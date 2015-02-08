package acv.mainwindow;

import java.awt.Component;
import java.awt.Toolkit;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 *
 * @author Oleksandr Bogovyk <obogovyk@gmail.com>
 */
public class MainWindow extends javax.swing.JFrame {
	
	private final static int MAX_LABEL_LENGTH = 50;

	private boolean isCalculating = false;
	private HashCalculator hashCalculator;
	HashInfo hashInfo = null;
    Component frame;


    /**
     * Creates new form NewJFrame
     */
    public MainWindow() {
        initComponents();
		
		List<Algorithm> list = Algorithm.getList();
		for(Algorithm a : list) {
			cmbAlgorithm.addItem(a);
		}
		
		clearData();
    }

	void calculateHash() {
		
		String filename = fieldFileName.getText();
		File file = new File( filename );
		if( !file.exists() ) {
			JOptionPane.showMessageDialog(frame, "Please select a file to calculate checksum. " + filename, 
					"Error", JOptionPane.ERROR_MESSAGE);
			return;
		}
		
		isCalculating = true;
		clearData();
		
		linkBuffer.setEnabled(false);
		buttonCalculate.setEnabled(false);
		buttonClear.setEnabled(false);
		menuItemExport.setEnabled(false);
		menuItemOpenFile.setEnabled(false);
		
        Algorithm algorithm = (Algorithm)cmbAlgorithm.getSelectedItem();
		hashCalculator = new HashCalculator();
		hashCalculator.setMainWindow(this);
		hashCalculator.setFileName( filename );
		hashCalculator.setAlgorithm( algorithm );
		hashCalculator.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String propertyName = evt.getPropertyName();
				if( propertyName.equals(HashCalculator.PROPERTY_PROGRESS_STRING) ) {
                                    String progressString = (String) evt.getNewValue();
                                    percentProgress.setText(progressString);
                                    //progressBar.setString( progressString );
				} else if ( propertyName.equals(HashCalculator.PROPERTY_PROGRESS_VALUE) ) {
                                    int progress = (Integer) evt.getNewValue();
                                    progressBar.setValue(progress);
				}
			}
		});
		
		hashCalculator.execute();
                linkBuffer.setEnabled(true);
	}
	void hashCalculated() {
		try {
			hashInfo = hashCalculator.get();
			fieldHash.setText( hashInfo.getHash() );
			fieldHash.setCaretPosition(0);
			
			setLabelText(labelInfoFilename, hashInfo.getFilename());
			setLabelText(labelInfoFilesize, hashInfo.getFilesize());
			setLabelText(labelInfoAlgorithm, hashInfo.getAlgorithm());
			setLabelText(labelInfoHash, hashInfo.getHash());
		} catch (InterruptedException | ExecutionException ex) {
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
			JOptionPane.showMessageDialog(frame, "An error occurred while calculating checksum.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			clearData();
		}
		linkBuffer.setEnabled(true);
		buttonCalculate.setEnabled(true);
		buttonClear.setEnabled(true);
		menuItemExport.setEnabled(true);
		menuItemOpenFile.setEnabled(true);
		isCalculating = false;
		progressBar.setValue(0);
		progressBar.setString( "" );
	}
	
    private void openDirectoryFile() {
		if( isCalculating )
			return;
        int foresult = hashFileChooserOpen.showOpenDialog(this);
        if (foresult == JFileChooser.APPROVE_OPTION) {
            File file = hashFileChooserOpen.getSelectedFile();
            fieldFileName.setText(file.getAbsolutePath());
            fieldFileName.setCaretPosition(0);
        } 
    }
    
	private void clearData() {
        //fieldFileName.setText("");
            hashInfo = null;
            fieldHash.setText("");
            labelInfoAlgorithm.setText("");
            labelInfoFilename.setText("");
            labelInfoFilesize.setText("");
            labelInfoHash.setText("");
	}

	private void exportHashInfo() {
		if( hashInfo == null ) {
			JOptionPane.showMessageDialog(frame, 
                            "No checksum found. Please open a file first.", 
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
			return;
		}
		int response = hashFileChooserSave.showOpenDialog(this);
		if( response != JFileChooser.APPROVE_OPTION )
			return;
		File file = hashFileChooserSave.getSelectedFile();
		try (FileOutputStream fos = new FileOutputStream(file)) {
			fos.write( hashInfo.createFileContent().getBytes() );
		} catch (IOException ex) {
			JOptionPane.showMessageDialog(frame, "An error occurred while exporting information.", 
					"Error", JOptionPane.ERROR_MESSAGE);
			Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
	
	private void copyToBuffer() {
		fieldHash.selectAll();
		fieldHash.copy();
		fieldHash.select(0, 0);
	}
	
	private void setLabelText(JLabel label, String text) {
		if( text.length() > MAX_LABEL_LENGTH )
			text = text.substring(0, MAX_LABEL_LENGTH) + "...";
		label.setText(text);
	}
	
	// Netbeans generated methods
	
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        hashFileChooserOpen = new javax.swing.JFileChooser();
        hashFileChooserSave = new javax.swing.JFileChooser();
        licenseDialog = new javax.swing.JDialog();
        jLabel4 = new javax.swing.JLabel();
        aboutDialog = new javax.swing.JDialog();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fieldFileName = new javax.swing.JTextField();
        cmbAlgorithm = new javax.swing.JComboBox<Algorithm>();
        fieldHash = new javax.swing.JTextField();
        buttonCalculate = new javax.swing.JButton();
        labelAlgorithm = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        labelOpen = new javax.swing.JLabel();
        buttonClear = new javax.swing.JButton();
        progressBar = new javax.swing.JProgressBar();
        labelInfoFilesize = new javax.swing.JLabel();
        labelInfoAlgorithm = new javax.swing.JLabel();
        labelInfoHash = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel14 = new javax.swing.JLabel();
        labelInfoFilename = new javax.swing.JLabel();
        percentProgress = new javax.swing.JLabel();
        linkBuffer = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        menuItemOpenFile = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        menuItemExport = new javax.swing.JMenuItem();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        menuItemExit = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();

        hashFileChooserOpen.setApproveButtonText("Open");
        hashFileChooserOpen.setApproveButtonToolTipText("Open file");
        hashFileChooserOpen.setDialogTitle("Open file");
        hashFileChooserOpen.setToolTipText("");

        hashFileChooserSave.setAcceptAllFileFilterUsed(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Text file (*.txt)", "txt");
        hashFileChooserSave.addChoosableFileFilter(filter);
        hashFileChooserSave.setDialogType(javax.swing.JFileChooser.SAVE_DIALOG);
        hashFileChooserSave.setApproveButtonToolTipText("Save file");
        hashFileChooserSave.setDialogTitle("Save file");
        hashFileChooserSave.setToolTipText("");

        licenseDialog.setTitle("License");

        jLabel4.setText("<html>\n   <center>ACV (Advanced Checksum Verifier)</center>\n    <strong><center>Copyright (C) 2015 Oleksandr Bogovyk, Andre Yakivchuk</center></strong><br>\n<p>\n    This program is free software: you can redistribute it and/or modify<br>\n    it under the terms of the GNU General Public License as published by<br>\n    the Free Software Foundation, either version 3 of the License, or<br>\n    (at your option) any later version.<br>\n<br>\n    This program is distributed in the hope that it will be useful,<br>\n    but WITHOUT ANY WARRANTY; without even the implied warranty of<br>\n    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the<br>\n    GNU General Public License for more details.<br>\n\n    You should have received a copy of the GNU General Public License<br>\n    along with this program.  If not, see <http://www.gnu.org/licenses/>.<br>\n</p>\nAlso add information on how to contact you by electronic and paper mail.\n</html>");

        javax.swing.GroupLayout licenseDialogLayout = new javax.swing.GroupLayout(licenseDialog.getContentPane());
        licenseDialog.getContentPane().setLayout(licenseDialogLayout);
        licenseDialogLayout.setHorizontalGroup(
            licenseDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(licenseDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        licenseDialogLayout.setVerticalGroup(
            licenseDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(licenseDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addContainerGap())
        );

        javax.swing.GroupLayout aboutDialogLayout = new javax.swing.GroupLayout(aboutDialog.getContentPane());
        aboutDialog.getContentPane().setLayout(aboutDialogLayout);
        aboutDialogLayout.setHorizontalGroup(
            aboutDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        aboutDialogLayout.setVerticalGroup(
            aboutDialogLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Advanced Checksum Verifier");
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("hash-logo.png")));
        setResizable(false);

        jLabel1.setText("File path:");

        jLabel2.setText("Algorithm:");

        jLabel3.setText("Checksum:");

        fieldFileName.setToolTipText("");

        cmbAlgorithm.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                cmbAlgorithmItemStateChanged(evt);
            }
        });

        fieldHash.setText("Checksum Value");

        buttonCalculate.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        buttonCalculate.setText("Calculate");
        buttonCalculate.setToolTipText("Calculate checksum");
        buttonCalculate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonCalculateActionPerformed(evt);
            }
        });

        labelAlgorithm.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelAlgorithm.setText("Algorithm Describe");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(102, 102, 102));
        jLabel5.setText("Details:");

        jLabel6.setForeground(new java.awt.Color(102, 102, 102));
        jLabel6.setText("File size:");

        jLabel7.setForeground(new java.awt.Color(102, 102, 102));
        jLabel7.setText("Algorithm:");

        jLabel8.setForeground(new java.awt.Color(102, 102, 102));
        jLabel8.setText("Checksum:");

        labelOpen.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelOpen.setForeground(java.awt.Color.blue);
        labelOpen.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        labelOpen.setText("<html><u>Open file...</u></html>");
        labelOpen.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        labelOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                labelOpenMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                labelOpenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                labelOpenMouseExited(evt);
            }
        });

        buttonClear.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        buttonClear.setText("Clear");
        buttonClear.setToolTipText("Clear data");
        buttonClear.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                buttonClearActionPerformed(evt);
            }
        });

        progressBar.setForeground(new java.awt.Color(51, 153, 0));
        progressBar.setToolTipText("");
        progressBar.setString("");
        progressBar.setStringPainted(true);
        progressBar.setVerifyInputWhenFocusTarget(false);

        labelInfoFilesize.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelInfoFilesize.setForeground(new java.awt.Color(102, 102, 102));
        labelInfoFilesize.setText("Filesize in Mb");

        labelInfoAlgorithm.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelInfoAlgorithm.setForeground(new java.awt.Color(102, 102, 102));
        labelInfoAlgorithm.setText("Algorithm Describe");

        labelInfoHash.setFont(new java.awt.Font("Tahoma", 3, 11)); // NOI18N
        labelInfoHash.setForeground(new java.awt.Color(102, 102, 102));
        labelInfoHash.setText("Checksum Value");

        jLabel14.setForeground(new java.awt.Color(102, 102, 102));
        jLabel14.setText("File name:");

        labelInfoFilename.setFont(new java.awt.Font("Tahoma", 2, 11)); // NOI18N
        labelInfoFilename.setForeground(new java.awt.Color(102, 102, 102));
        labelInfoFilename.setText("Appropriate filename");

        percentProgress.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        percentProgress.setText("0%");

        linkBuffer.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        linkBuffer.setForeground(new java.awt.Color(102, 102, 102));
        linkBuffer.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        linkBuffer.setText("Copy to clipboard");
        linkBuffer.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        linkBuffer.setEnabled(false);
        linkBuffer.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                linkBufferKeyPressed(evt);
            }
        });

        jMenu1.setText("File");

        menuItemOpenFile.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.CTRL_MASK));
        menuItemOpenFile.setText("Open");
        menuItemOpenFile.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemOpenFileActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemOpenFile);

        jMenu3.setText("Export");

        menuItemExport.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.CTRL_MASK));
        menuItemExport.setText("Text file (*.txt)");
        menuItemExport.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExportActionPerformed(evt);
            }
        });
        jMenu3.add(menuItemExport);

        jMenu1.add(jMenu3);
        jMenu1.add(jSeparator3);

        menuItemExit.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F4, java.awt.event.InputEvent.ALT_MASK));
        menuItemExit.setText("Exit");
        menuItemExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuItemExitActionPerformed(evt);
            }
        });
        jMenu1.add(menuItemExit);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Help");

        jMenuItem4.setText("License");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem4);

        jMenuItem5.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, java.awt.event.InputEvent.ALT_MASK));
        jMenuItem5.setText("About");
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(fieldHash, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cmbAlgorithm, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(labelAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, 184, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(progressBar, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(percentProgress, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(fieldFileName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jSeparator2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(linkBuffer)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(labelOpen, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(buttonClear)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(buttonCalculate))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(labelInfoHash, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE)
                            .addComponent(labelInfoAlgorithm, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelInfoFilesize, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(labelInfoFilename, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {buttonCalculate, buttonClear});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {labelInfoAlgorithm, labelInfoFilename, labelInfoFilesize, labelInfoHash});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel14, jLabel5, jLabel6, jLabel7, jLabel8});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(fieldFileName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(labelOpen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(cmbAlgorithm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(labelAlgorithm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fieldHash, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(progressBar, javax.swing.GroupLayout.DEFAULT_SIZE, 21, Short.MAX_VALUE)
                    .addComponent(percentProgress))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 3, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(buttonCalculate)
                    .addComponent(buttonClear)
                    .addComponent(linkBuffer))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(labelInfoFilename))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(labelInfoFilesize))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(labelInfoAlgorithm))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(labelInfoHash))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {fieldHash, progressBar});

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

		
    private void buttonCalculateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonCalculateActionPerformed
		calculateHash();
    }//GEN-LAST:event_buttonCalculateActionPerformed

    private void buttonClearActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_buttonClearActionPerformed
		clearData();
		fieldFileName.setText("");
                linkBuffer.setEnabled(false);
                percentProgress.setText("0%");
    }//GEN-LAST:event_buttonClearActionPerformed

    private void labelOpenMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelOpenMouseClicked
        openDirectoryFile();
    }//GEN-LAST:event_labelOpenMouseClicked

    private void menuItemOpenFileActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemOpenFileActionPerformed
        openDirectoryFile();
    }//GEN-LAST:event_menuItemOpenFileActionPerformed

    private void menuItemExportActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExportActionPerformed
        exportHashInfo();
    }//GEN-LAST:event_menuItemExportActionPerformed

    private void labelOpenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelOpenMouseEntered
        labelOpen.setText("<html>Open file...</html>");
    }//GEN-LAST:event_labelOpenMouseEntered

    private void labelOpenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_labelOpenMouseExited
        labelOpen.setText("<html><u>Open file...</u></html>");
    }//GEN-LAST:event_labelOpenMouseExited

    private void cmbAlgorithmItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_cmbAlgorithmItemStateChanged
        Algorithm algorithm = (Algorithm)cmbAlgorithm.getSelectedItem();
		if( algorithm == null ) {
			labelAlgorithm.setText("");
		} else {
			labelAlgorithm.setText(algorithm.getDescription());
		}
    }//GEN-LAST:event_cmbAlgorithmItemStateChanged

    private void menuItemExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuItemExitActionPerformed
        Object[] options = {"Yes", "No"};
        int status = JOptionPane.showOptionDialog(frame, 
                "Are you sure you want to exit?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
                );
        System.out.println(status);
        if (JOptionPane.NO_OPTION != status) {
            System.exit(0);
        }
    }//GEN-LAST:event_menuItemExitActionPerformed

    private void linkBufferKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_linkBufferKeyPressed
        copyToBuffer();
    }//GEN-LAST:event_linkBufferKeyPressed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        licenseDialog.pack();
        licenseDialog.setLocationRelativeTo(frame);
        licenseDialog.setVisible(true);
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

	
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JDialog aboutDialog;
    private javax.swing.JButton buttonCalculate;
    private javax.swing.JButton buttonClear;
    private javax.swing.JComboBox<Algorithm> cmbAlgorithm;
    private javax.swing.JTextField fieldFileName;
    private javax.swing.JTextField fieldHash;
    private javax.swing.JFileChooser hashFileChooserOpen;
    private javax.swing.JFileChooser hashFileChooserSave;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JLabel labelAlgorithm;
    private javax.swing.JLabel labelInfoAlgorithm;
    private javax.swing.JLabel labelInfoFilename;
    private javax.swing.JLabel labelInfoFilesize;
    private javax.swing.JLabel labelInfoHash;
    private javax.swing.JLabel labelOpen;
    private javax.swing.JDialog licenseDialog;
    private javax.swing.JLabel linkBuffer;
    private javax.swing.JMenuItem menuItemExit;
    private javax.swing.JMenuItem menuItemExport;
    private javax.swing.JMenuItem menuItemOpenFile;
    private javax.swing.JLabel percentProgress;
    private javax.swing.JProgressBar progressBar;
    // End of variables declaration//GEN-END:variables
}
