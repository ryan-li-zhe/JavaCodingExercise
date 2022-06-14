package ca.gc.cra.javacodingexercise;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class HomeView extends JFrame implements ActionListener, FocusListener {
    String errorMessage = "";
    List<String[]> resultToPrint = new ArrayList<>();
    // Frame
    JFrame homeFrame = new JFrame(Settings.APP_NAME);

    // panel
    JPanel panel = new JPanel(); // the top level panel to carry all the components.

    // labels
    JLabel welcomeLabel = new JLabel(Settings.WELCOME_LABEL, SwingConstants.CENTER);
    JLabel tanLabel = new JLabel(Settings.TAN_LABEL);
    JLabel notLabel = new JLabel(Settings.NOT_LABEL);
    JLabel cityLabel = new JLabel(Settings.CITY_LABEL);
    JLabel provinceLabel = new JLabel(Settings.PROVINCE_LABEL);
    JLabel piLabel = new JLabel(Settings.PI_LABEL);
    JLabel riLabel = new JLabel(Settings.RI_LABEL);

    // input fields
    JTextField tanField = new JTextField(Settings.FIELD_LENGTH);
    JTextField notField = new JTextField(Settings.FIELD_LENGTH);
    JTextField cityField = new JTextField(Settings.FIELD_LENGTH);
    JTextField provinceField = new JTextField(Settings.FIELD_LENGTH);
    JTextField piField = new JTextField(Settings.FIELD_LENGTH);
    JTextField riField = new JTextField(Settings.FIELD_LENGTH);
    JTextField filePath = new JTextField(Settings.FIELD_LENGTH);
    JTextArea errorArea = new JTextArea();

    // buttons
    JButton submitButton = new JButton(Settings.SUBMIT_BUTTON);
    JButton importButton = new JButton(Settings.IMPORT_BUTTON);
    JButton printButton = new JButton(Settings.PRINT_BUTTON);

    public HomeView() {
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        welcomeLabel.setBorder(new EmptyBorder(20, 20, 20, 20));

        // layout
        panel.setLayout(new BorderLayout());
        panel.add(welcomeLabel, BorderLayout.NORTH);

        JPanel labelsPannel = new JPanel();
        labelsPannel.setBorder(new EmptyBorder(10, 10, 10, 10));
        labelsPannel.setLayout(new GridLayout(6, 1, 10, 10));
        labelsPannel.add(tanLabel);
        labelsPannel.add(notLabel);
        labelsPannel.add(cityLabel);
        labelsPannel.add(provinceLabel);
        labelsPannel.add(piLabel);
        labelsPannel.add(riLabel);
        panel.add(labelsPannel, BorderLayout.WEST);

        JPanel fieldsPannel = new JPanel();
        fieldsPannel.setLayout(new GridLayout(6, 1, 10, 10));
        // set a field name for each field, so we are able to know which field
        // gained/lost focus.
        tanField.setName(Settings.TAN_FIELD_NAME); 
        notField.setName(Settings.NOT_FIELD_NAME);
        cityField.setName(Settings.CITY_FIELD_NAME);
        provinceField.setName(Settings.PROVINCE_FIELD_NAME);
        piField.setName(Settings.PI_FIELD_NAME);
        riField.setName(Settings.RI_FIELD_NAME);
        
        fieldsPannel.add(tanField);
        fieldsPannel.add(notField);
        fieldsPannel.add(cityField);
        fieldsPannel.add(provinceField);
        fieldsPannel.add(piField);
        fieldsPannel.add(riField);

        // add focus listeners for text fields.
        tanField.addFocusListener(this);
        notField.addFocusListener(this);
        cityField.addFocusListener(this);
        provinceField.addFocusListener(this);
        piField.addFocusListener(this);
        riField.addFocusListener(this);

        panel.add(fieldsPannel, BorderLayout.CENTER);

        JPanel errorMessagePannel = new JPanel();
        errorMessagePannel.add(errorArea);
        panel.add(errorMessagePannel, BorderLayout.EAST);

        JPanel buttonsPannel = new JPanel();
        buttonsPannel.setLayout(new GridBagLayout());
        buttonsPannel.setBorder(new EmptyBorder(20, 20, 20, 20));
        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.weighty = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        buttonsPannel.add(submitButton, c);
        submitButton.addActionListener(this);
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 1;
        buttonsPannel.add(importButton, c);
        importButton.addActionListener(this);
        c.gridx = 1;
        c.gridy = 1;
        c.gridwidth = 1;
        buttonsPannel.add(filePath, c);
        c.gridx = 0;
        c.gridy = 2;
        c.gridwidth = 1;
        buttonsPannel.add(printButton, c);
        printButton.addActionListener(this);
        panel.add(buttonsPannel, BorderLayout.SOUTH);

        homeFrame.add(panel);
        homeFrame.pack();
        homeFrame.setLocationRelativeTo(null); // set frame to screen center.
        homeFrame.setSize(Settings.FRAME_WIDTH, Settings.FRAME_LONGTH);
        homeFrame.setVisible(true);
    }

    /**
     * To handle clicking on "Submit" & "Import" button.
     */
    public void actionPerformed(ActionEvent evt) {
        String action = evt.getActionCommand();
        if (Settings.SUBMIT_BUTTON.equals(action)) {
            // validate all textFields again.
            boolean hasInvalid = false;
            List<JTextField> fields = new ArrayList<>();
            fields.add(tanField);
            fields.add(notField);
            fields.add(cityField);
            fields.add(provinceField);
            fields.add(piField);
            fields.add(riField);
            for(JTextField f: fields) {
                if(hasInvalid) {
                    errorArea.setText(errorMessage);
                    JOptionPane.showMessageDialog(null, errorMessage, "Error...", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                errorMessage = this.validateTextField(f);
                if(errorMessage.length() != 0) {
                    hasInvalid = true;
                }
            }
            if (errorMessage.length() == 0) {
                String[] inputRow = new String[Settings.FIELDS_COUNT];
                inputRow[0] = Settings.TAN_FIELD_NAME + ": " + tanField.getText().trim() + "\t";
                inputRow[1] = Settings.NOT_FIELD_NAME + ": " + notField.getText().trim() + "\t";
                inputRow[2] = Settings.CITY_FIELD_NAME + ": " + cityField.getText().trim() + "\t";
                inputRow[3] = Settings.PROVINCE_FIELD_NAME + ": " + provinceField.getText().trim() + "\t";
                inputRow[4] = Settings.PI_FIELD_NAME + ": " + piField.getText().trim() + "\t";
                inputRow[5] = Settings.RI_FIELD_NAME + ": " + riField.getText().trim() + "\t";
                resultToPrint.add(inputRow);
                System.out.println("\nYou just submitted below record:");
                this.writeToConsole(resultToPrint);
            } else {
                errorArea.setText(errorMessage);
                JOptionPane.showMessageDialog(null, errorMessage, "Error...", JOptionPane.ERROR_MESSAGE);
            }

        } else if (Settings.IMPORT_BUTTON.equals(action)) {
            errorArea.setText(""); // reset error message when clicking on import button.
            JFileChooser fileChooser = new JFileChooser(Settings.DEFAULT_IMPORT_FOLDER);
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                List<String[]> rows = readCsvFile(fileChooser);
                
                if(rows == null) {
                    errorMessage = "Please choose a csv file.";
                    JOptionPane.showMessageDialog(null, errorMessage, "Error...", JOptionPane.ERROR_MESSAGE);
                    errorArea.setText(errorMessage);
                    return;
                }

                System.out.println("\nYou just imported below record:");
                this.writeToConsole(rows);
                resultToPrint.addAll(rows);
            }
        } else if (Settings.PRINT_BUTTON.equals(action)) {
            System.out.println("\nBelow are all records we have:");
            writeToConsole(resultToPrint);
        } else {
            System.out.println("clicked on a weird button...");
        }

    }
    
    /** Accepts a List<String[]>, then print it to console.
     * 
     * @param rows The rows that are retrieved from csv file.
     */
    public void writeToConsole(List<String[]> rows) {
        for (String[] row : rows) {
            for (String field : row) {
                System.out.print(field + "\t");
            }
            System.out.print("\n");
        }
    }

    /** Read the file that is chosen by JFileChooser, then return all the rows as a ArrayList<String[]>.
     *
     * @param fileChooser  The JFileChooser that for user to pick up a file.
     * @return             An ArrayList<String[]> that carries all the rows of the chosen csv file. Or, return null if selected file is not a csv file.
     */
    public List<String[]> readCsvFile(JFileChooser fileChooser) {
        List<String[]> rows = new ArrayList<>();
        BufferedReader brd = null;
        String[] oneRow;
        File selectedFile = fileChooser.getSelectedFile();
        if(selectedFile == null) {
            return null;
        }
        String selectedFilePath = selectedFile.getAbsolutePath();
        
        if(selectedFilePath.endsWith(".csv")) {
            filePath.setText(selectedFilePath);
            try {
                String[] headerRow = new String[0];
                brd = new BufferedReader(new FileReader(selectedFile));
                while (brd.ready()) {
                    String st = brd.readLine();
                    oneRow = st.split(",|\\s|;");
                    if(headerRow.length == 0) { // it's header row, do not add to rows.
                        headerRow = oneRow;
                    } else { // it's value row, add to rows as key->value.
                        String[] resultRow = new String[oneRow.length];
                        for(int i=0; i<oneRow.length; i++) {
                            resultRow[i] = headerRow[i] + ": " + oneRow[i].trim() + "\t";
                        }
                        rows.add(resultRow);
                    }
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(brd != null) {
                    try {
                        brd.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    } finally {
                        brd = null;
                    }
                }
            }
            return rows;
        } 
        return null;    
    }
    
    /**
     * When focused on a field, do nothing.
     */
    public void focusGained(FocusEvent evt) {
        // Do nothing.
    }

    /**
     * When a text field lost focus, validate the input value.
     * 
     */
    public void focusLost(FocusEvent evt) {
        if (evt.getSource() instanceof JTextField) { // only handle focus lost for JTextField.
            errorArea.setText(""); // reset error message
            JTextField textFieldClicked = (JTextField) evt.getSource();
            errorMessage = this.validateTextField(textFieldClicked);
            if (errorMessage.length() != 0) {
                errorArea.setText(errorMessage);
                errorArea.setForeground(Color.RED);
            } 
        }
    }
    
    /**
     * Validate if a JTextField has a valid input value.
     * 
     * @param   field  The JTextField object.
     * @return         An error message, or empty string if there is no error.
     */
    public String validateTextField(JTextField field) {
        String returnable = "";
        String textFieldName = field.getName();
        String fieldContent = field.getText().trim();
        if (fieldContent.length() == 0) {
            returnable = textFieldName + " cannot be null.";
        } else if (Settings.PI_FIELD_NAME.equals(textFieldName) || Settings.RI_FIELD_NAME.equals(textFieldName)) {
            int content = -1;
            try {
                content = Integer.parseInt(fieldContent);
            } catch (NumberFormatException ex) {
                if (returnable.length() > 0) {
                    returnable = returnable + "\nPlease enter an integer for " + textFieldName + ".";
                } else {
                    return "Please enter an integer for " + textFieldName + ".";
                }
            }
            if (content < 0) {
                if (returnable.length() > 0) {
                    returnable = returnable + "\nPlease enter a positive integer for " + textFieldName + ".";
                } else {
                    returnable = "Please enter a positive integer for " + textFieldName + ".";
                }
            }
        } else if (Settings.TAN_FIELD_NAME.equals(textFieldName)) {
            if (fieldContent.length() == 9) {
                if (!fieldContent.toLowerCase().startsWith("t")) {
                    returnable = "TAN must start with T.";                  
                } else {
                    String s = fieldContent.substring(1);
                    try {
                        int myNum = Integer.parseInt(s);
                        if((myNum + "").length()!=8) {
                            if (returnable.length() > 0) {
                                returnable = returnable + "\nTAN must contain 8 digits.";
                            } else {
                                return "TAN must contain 8 digits.";
                            }
                        }
                    } catch (NumberFormatException ex) {
                        if (returnable.length() > 0) {
                            returnable = returnable + "\nPlease enter a 8 digits after T or t for " + textFieldName + ".";
                        } else {
                            returnable = "Please enter 8 digits after T or t for " + textFieldName + ".";
                        }
                    }
                }               
            } else {
                returnable = "TAN must contain 9 letters.";
            }
        } else if (Settings.PROVINCE_FIELD_NAME.equals(textFieldName)) {
            String[] provinces = { "Alberta", "British Columbia", "Manitoba", "New Brunswick",
                    "Newfoundland and Labrador", "Nova Scotia", "Ontario", "Prince Edward Island", "Quebec",
                    "Saskatchewan", "AB", "BC", "MB", "NB", "NL", "NS", "ON", "PE", "QC", "SK" };
            boolean contains = Arrays.stream(provinces).anyMatch(pr -> pr.equalsIgnoreCase(fieldContent));
            if (!contains) {
                returnable = "Province must be a Canadian province.";
            }
        }
        errorMessage = returnable;
        return returnable;
    }

}