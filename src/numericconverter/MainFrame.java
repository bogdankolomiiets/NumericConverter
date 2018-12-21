package numericconverter;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.event.*;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

public class MainFrame extends JFrame {

    private final String TEXT = "Введите число";
    private JFrame frame;
    private JPanel topPanel;
    private JPanel panelForMenu;
    private JPanel panelForRadioButton;
    private JPanel centerPanel;
    private JTextField textFieldValue;
    private JTextField textFieldBinary;
    private JTextField textFieldOctal;
    private JTextField textFieldDecimal;
    private JTextField textFieldHexadecimal;

    private JLabel labelValue;
    private JLabel labelBinary;
    private JLabel labelOctal;
    private JLabel labelDecimal;
    private JLabel labelHexadecimal;

    private JPanel bottomPanel;
    private JTextField mistakeTextField;

    private ButtonGroup group;
    private JRadioButton radioBinary;
    private JRadioButton radioOctal;
    private JRadioButton radioDecimal;
    private JRadioButton radioHexadecimal;
    private Timer timer = new Timer();
    private TimerTask timerTask;
    private boolean timerStart;

    private JPopupMenu popupMenu = new JPopupMenu();
    private JPopupMenu popupPaste = new JPopupMenu();
    private JMenuItem copyItem = new JMenuItem("Копировать");
    private JMenuItem pasteItem = new JMenuItem("Вставить");

    boolean switchForMistake;

    MainFrame () {
        frame = new JFrame("Конвертер систем счисления");
        frame.setSize(500, 355);
        frame.setLocation((Toolkit.getDefaultToolkit().getScreenSize().width - frame.getSize().width) / 2, (Toolkit.getDefaultToolkit().getScreenSize().height - frame.getSize().width) / 2);
        frame.setLayout(new BorderLayout());
        initComponents();
        listeners();
        ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("nc.png"));
        frame.setIconImage(icon.getImage());
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void initComponents() {
        topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        panelForMenu = new JPanel();
        panelForMenu.setLayout(new BorderLayout());
        topPanel.add(panelForMenu, BorderLayout.NORTH);
        panelForRadioButton = new JPanel();
        panelForRadioButton.setLayout(new FlowLayout());
        topPanel.add(panelForRadioButton, BorderLayout.SOUTH);
        frame.add(topPanel, BorderLayout.NORTH);

        Menu menu = new Menu(panelForMenu, frame);

        radioBinary = new JRadioButton();
        radioBinary.setText("Двоичная");
        radioOctal = new JRadioButton();
        radioOctal.setText("Восьмеричная");
        radioDecimal = new JRadioButton();
        radioDecimal.setSelected(true);
        radioDecimal.setText("Десятичная");
        radioHexadecimal = new JRadioButton();
        radioHexadecimal.setText("Шестнадцатеричная");
        //add radioButtons to group
        group = new ButtonGroup();
        group.add(radioBinary);
        group.add(radioOctal);
        group.add(radioDecimal);
        group.add(radioHexadecimal);
        //add radioButtons inside topPanel
        panelForRadioButton.add(radioBinary);
        panelForRadioButton.add(radioOctal);
        panelForRadioButton.add(radioDecimal);
        panelForRadioButton.add(radioHexadecimal);

        centerPanel = new JPanel();
        FlowLayout flowLayout = new FlowLayout();
        flowLayout.setAlignment(SwingConstants.HORIZONTAL);
        centerPanel.setLayout(flowLayout);
        frame.add(centerPanel, BorderLayout.CENTER);

        labelValue = new JLabel("Число для перевода:");
        centerPanel.add(labelValue);

        textFieldValue = new JTextField();
        textFieldValue.setPreferredSize(new Dimension(frame.getSize().width - 25, 30));
        textFieldValue.setText(TEXT);
        textFieldValue.setBorder(BorderFactory.createEtchedBorder(Color.red, Color.YELLOW));
        textFieldValue.setFont(new Font("Courier New", Font.BOLD, 20));
        centerPanel.add(textFieldValue);

        labelBinary = new JLabel("В двоичной системе:");
        centerPanel.add(labelBinary);

        textFieldBinary = new JTextField();
        textFieldBinary.setEditable(false);
        textFieldBinary.setPreferredSize(new Dimension(frame.getSize().width - 25, 30));
        textFieldBinary.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.YELLOW));
        centerPanel.add(textFieldBinary);

        labelOctal = new JLabel("В восьмеричной системе:");
        centerPanel.add(labelOctal);

        textFieldOctal = new JTextField();
        textFieldOctal.setEditable(false);
        textFieldOctal.setPreferredSize(new Dimension(frame.getSize().width - 25, 30));
        textFieldOctal.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.YELLOW));
        centerPanel.add(textFieldOctal);

        labelDecimal = new JLabel("В десятичной системе:");
        labelDecimal.setVisible(false);
        centerPanel.add(labelDecimal);

        textFieldDecimal = new JTextField();
        textFieldDecimal.setEditable(false);
        textFieldDecimal.setVisible(false);
        textFieldDecimal.setPreferredSize(new Dimension(frame.getSize().width - 25, 30));
        textFieldDecimal.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.YELLOW));
        centerPanel.add(textFieldDecimal);

        labelHexadecimal = new JLabel("В шестнадцатеричной системе:");
        centerPanel.add(labelHexadecimal);

        textFieldHexadecimal = new JTextField();
        textFieldHexadecimal.setEditable(false);
        textFieldHexadecimal.setPreferredSize(new Dimension(frame.getSize().width - 25, 30));
        textFieldHexadecimal.setBorder(BorderFactory.createEtchedBorder(Color.GREEN, Color.YELLOW));
        centerPanel.add(textFieldHexadecimal);
        listenerForTextFieldValue();


        bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout());
        mistakeTextField = new JTextField();
        mistakeTextField.setEditable(false);
        mistakeTextField.setPreferredSize(new Dimension(frame.getSize().width, 20));
        mistakeTextField.setHorizontalAlignment(SwingConstants.CENTER);
        mistakeTextField.setFont(new Font("Courier New", Font.BOLD, 15));
        mistakeTextField.setBackground(Color.GREEN);
        bottomPanel.add(mistakeTextField);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        popupMenu.add(copyItem);
        popupPaste.add(pasteItem);
        textFieldValue.setComponentPopupMenu(popupPaste);
        textFieldBinary.setComponentPopupMenu(popupMenu);
        textFieldOctal.setComponentPopupMenu(popupMenu);
        textFieldDecimal.setComponentPopupMenu(popupMenu);
        textFieldHexadecimal.setComponentPopupMenu(popupMenu);

    }

    public void listeners(){
        //Listener for close operation
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(frame, "Вы действительно хотите выйти?", "Закрыть программу?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION){
                    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
                } else frame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
            }
        });

        copyItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTextField textField = (JTextField) popupMenu.getInvoker();
                Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(textField.getText()), null);
            }
        });

        pasteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    textFieldValue.setText(Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor).toString());
                } catch (UnsupportedFlavorException e1) {
                    e1.printStackTrace();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        radioBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
                textFieldBinary.setVisible(false);
                textFieldOctal.setVisible(true);
                textFieldDecimal.setVisible(true);
                textFieldHexadecimal.setVisible(true);

                labelBinary.setVisible(false);
                labelOctal.setVisible(true);
                labelDecimal.setVisible(true);
                labelHexadecimal.setVisible(true);
            }
        });

        radioOctal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
                textFieldBinary.setVisible(true);
                textFieldOctal.setVisible(false);
                textFieldDecimal.setVisible(true);
                textFieldHexadecimal.setVisible(true);

                labelBinary.setVisible(true);
                labelOctal.setVisible(false);
                labelDecimal.setVisible(true);
                labelHexadecimal.setVisible(true);
            }
        });

        radioDecimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
                textFieldBinary.setVisible(true);
                textFieldOctal.setVisible(true);
                textFieldDecimal.setVisible(false);
                textFieldHexadecimal.setVisible(true);

                labelBinary.setVisible(true);
                labelOctal.setVisible(true);
                labelDecimal.setVisible(false);
                labelHexadecimal.setVisible(true);
            }
        });

        radioHexadecimal.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearTextFields();
                textFieldBinary.setVisible(true);
                textFieldOctal.setVisible(true);
                textFieldDecimal.setVisible(true);
                textFieldHexadecimal.setVisible(false);

                labelBinary.setVisible(true);
                labelOctal.setVisible(true);
                labelDecimal.setVisible(true);
                labelHexadecimal.setVisible(false);
            }
        });

        textFieldValue.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                if (textFieldValue.getText().equals(TEXT)) {
                    textFieldValue.setText("");
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (textFieldValue.getText().length() == 0 ) {
                    textFieldValue.setText(TEXT);
                }
            }
        });
    }

    private void clearTextFields(){
        textFieldValue.setText(TEXT);
        textFieldBinary.setText("");
        textFieldOctal.setText("");
        textFieldDecimal.setText("");
        textFieldHexadecimal.setText("");
    }

    public void listenerForTextFieldValue() {
        textFieldValue.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                check();
                convert();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                check();
                convert();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                check();
                convert();
            }

            private void check(){
                if (textFieldValue.getText().length() == 0){
                    textFieldBinary.setText("");
                    textFieldOctal.setText("");
                    textFieldDecimal.setText("");
                    textFieldHexadecimal.setText("");
                }
            }

            public void convert() {
                //if the user makes a mistake and he chooses new JRadioButton, then stops the timer
                if (group.getSelection().isSelected() && textFieldValue.getText().equals(TEXT)){
                    noMistake();
                }

                if (!textFieldValue.getText().equals(TEXT) && !textFieldValue.getText().equals("")) {
                    if (radioBinary.isSelected()) {
                        if (textFieldValue.getText().matches("[0-1]+")) {
                            String value = textFieldValue.getText();
                            noMistake();
                            textFieldOctal.setText(convertFromBinaryToOctal(value));
                            textFieldDecimal.setText(String.valueOf(convertFromBinaryToDecimal(value)));
                            textFieldHexadecimal.setText(convertFromBinaryToHexadecimal(value));
                        } else mistake();
                    } else if (radioOctal.isSelected()) {
                        if (textFieldValue.getText().matches("[0-7]+")) {
                            String value = textFieldValue.getText();
                            noMistake();
                            textFieldBinary.setText(convertFromOctalToBinary(value));
                            textFieldDecimal.setText(convertFromOctalToDecimal(value));
                            textFieldHexadecimal.setText(convertFromOctalToHexadecimal(textFieldBinary.getText()));
                        } else mistake();
                    } else if (radioDecimal.isSelected()) {
                        if (textFieldValue.getText().matches("[0-9]+")) {
                            Long value = Long.valueOf(textFieldValue.getText());
                            noMistake();
                            textFieldBinary.setText(convertFromDecimalToBinary(value));
                            textFieldOctal.setText(convertFromDecimalToOctal(value));
                            textFieldHexadecimal.setText(convertFromDecimalToHexadecimal(value));
                        } else mistake();
                    } else if (radioHexadecimal.isSelected()) {
                        if (textFieldValue.getText().matches("[0-9, A-F, a-f]+")) {
                            String value = textFieldValue.getText();
                            noMistake();
                            textFieldBinary.setText(convertFromHexadecimalToBinary(value));
                            textFieldOctal.setText(convertFromHexadecimalToOctal(textFieldBinary.getText()));
                            textFieldDecimal.setText(convertFromHexadecimalToDecimal(value));
                        } else mistake();
                    }
                }
            }
        });
    }

    private void mistake() {
        //if user is mistaken two or more times, then timer doesn't start
        if (!timerStart) {
            mistakeTextField.setText("Ошибка ввода");
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    mistakeTextField.setBackground(switchForMistake == true ? Color.RED : Color.GREEN);
                    switchForMistake = (switchForMistake == true ? false : true);
                }
            };
            timer.schedule(timerTask, 500, 500);
            timerStart = true;
        }
    }

    private void noMistake(){
        //Stop the timerTask and timer
        if (timerStart) {
            timerTask.cancel();
            timer.purge();
            timerStart = false;
        }
        mistakeTextField.setText("");
        mistakeTextField.setBackground(Color.GREEN);
    }

    public String convertFromBinaryToOctal(String value){
        String result = "";
        StringBuffer buffer = new StringBuffer(value).reverse();
        StringBuffer reverseThree;
        for (String s : buffer.toString().split("(?<=\\G.{3})")) {
            reverseThree = new StringBuffer(s).reverse();
            result = binaryToOctal(reverseThree.toString()) + result;
        }
            return result;

    }

    public long convertFromBinaryToDecimal(String value){
        long result = 0;
        int length = value.length()-1;
        for (String s : value.split("")) {
            int i = Integer.parseInt(s);
            result += i * Math.pow(2, length);
            length--;
        }
        return result;
    }

    public String convertFromBinaryToHexadecimal(String value){
        String result = "";
        StringBuffer buffer = new StringBuffer(value).reverse();
        StringBuffer reverseFour;
        for (String s : buffer.toString().split("(?<=\\G.{4})")){
            reverseFour = new StringBuffer(s).reverse();
            result = binaryToHexadecimal(reverseFour.toString()) + result;
        }
        return result;
    }

    public String convertFromOctalToBinary(String value) {
        String result = "";
        for (String s : value.split("")) {
            result += octalToBinary(s);
        }

        while (result.substring(0, 1).equals("0") && !result.equals("0")) {
            result = result.substring(1, result.length());
        }

        return result;
    }

    public String convertFromOctalToDecimal(String value){
        long result = 0;
        int length = value.length()-1;
        for (String s : value.split("")){
            int i = Integer.parseInt(s);
            result += i * Math.pow(8, length);
            length--;
        }
        return String.valueOf(result);
    }

    public String convertFromOctalToHexadecimal(String value){
        String result = convertFromBinaryToHexadecimal(value);
        return result;
    }

    public String convertFromDecimalToBinary(long value){
        String zeroOne = "";
        while (value >= 1){
            long result = value / 2;
            long m = value % 2;
            zeroOne = value - m == value ? "0" : "1";
            return zeroOne = convertFromDecimalToBinary(result) + zeroOne;
        }
        return zeroOne;
    }

    public String convertFromDecimalToOctal(long value){
        String result = "";
        if (value > 7){
            long res = value / 8;
            result += value % 8;
            return convertFromDecimalToOctal(res) + result;
        }
        else return String.valueOf(value);
    }

    public String convertFromDecimalToHexadecimal(long value){
        String result = "";
        if (value > 15){
            long res = value / 16;
            result += value % 16;
            return convertFromDecimalToHexadecimal(res) + replaceNumToChar(Long.valueOf(result));
        } else return replaceNumToChar(value);
    }

    public String convertFromHexadecimalToBinary(String value){
        String result = "";
        for (String s : value.split("")){
            result += hexadecimalToBinary(s);
        }

        while (result.substring(0, 1).equals("0") && !result.equals("0")){
            result = result.substring(1, result.length());
        }

        return result;
    }

    public String convertFromHexadecimalToOctal(String binaryValue){
        String result = convertFromBinaryToOctal(binaryValue);
        return result;
    }

    public String convertFromHexadecimalToDecimal(String value){
        long result = 0;
        int length = value.length()-1;
        for (String s : value.split("")) {
            int i = Integer.parseInt(replaceCharToNum(s));
            result += i * Math.pow(16, length);
            length--;
        }
        return String.valueOf(result);
    }

    private String replaceCharToNum(String s){
        if (s.equals("A") || s.equals("a")) s = "10";
        if (s.equals("B") || s.equals("b")) s = "11";
        if (s.equals("C") || s.equals("c")) s = "12";
        if (s.equals("D") || s.equals("d")) s = "13";
        if (s.equals("E") || s.equals("e")) s = "14";
        if (s.equals("F") || s.equals("f")) s = "15";

        return s;
    }

    private String replaceNumToChar(long i){
        if (i <= 9 ) return String.valueOf(i);
        if (i == 10) return "A";
        if (i == 11) return "B";
        if (i == 12) return "C";
        if (i == 13) return "D";
        if (i == 14) return "E";
        if (i == 15) return "F";
        return "";
    }

    private String binaryToOctal(String s){
        if (s.length() == 1) s = "00" + s;
        if (s.length() == 2) s = "0" + s;
        if (s.equals("000")) return "0";
        if (s.equals("001")) return "1";
        if (s.equals("010")) return "2";
        if (s.equals("011")) return "3";
        if (s.equals("100")) return "4";
        if (s.equals("101")) return "5";
        if (s.equals("110")) return "6";
        if (s.equals("111")) return "7";
        return s;
    }

    private String binaryToHexadecimal(String s){
        if (s.length() == 1) s = "000" + s;
        if (s.length() == 2) s = "00" + s;
        if (s.length() == 3) s = "0" + s;
        if (s.equals("0000")) return "0";
        if (s.equals("0001")) return "1";
        if (s.equals("0010")) return "2";
        if (s.equals("0011")) return "3";
        if (s.equals("0100")) return "4";
        if (s.equals("0101")) return "5";
        if (s.equals("0110")) return "6";
        if (s.equals("0111")) return "7";
        if (s.equals("1000")) return "8";
        if (s.equals("1001")) return "9";
        if (s.equals("1010")) return "A";
        if (s.equals("1011")) return "B";
        if (s.equals("1100")) return "C";
        if (s.equals("1101")) return "D";
        if (s.equals("1110")) return "E";
        if (s.equals("1111")) return "F";
        return s;
    }

    private String octalToBinary(String s){
        if (s.equals("0")) return "000";
        if (s.equals("1")) return "001";
        if (s.equals("2")) return "010";
        if (s.equals("3")) return "011";
        if (s.equals("4")) return "100";
        if (s.equals("5")) return "101";
        if (s.equals("6")) return "110";
        if (s.equals("7")) return "111";
        return s;
    }

    private String hexadecimalToBinary(String s){
        if (s.equals("0")) return "0000";
        if (s.equals("1")) return "0001";
        if (s.equals("2")) return "0010";
        if (s.equals("3")) return "0011";
        if (s.equals("4")) return "0100";
        if (s.equals("5")) return "0101";
        if (s.equals("6")) return "0110";
        if (s.equals("7")) return "0111";
        if (s.equals("8")) return "1000";
        if (s.equals("9")) return "1001";
        if (s.equals("A") || s.equals("a")) return "1010";
        if (s.equals("B") || s.equals("b")) return "1011";
        if (s.equals("C") || s.equals("c")) return "1100";
        if (s.equals("D") || s.equals("d")) return "1101";
        if (s.equals("E") || s.equals("e")) return "1110";
        if (s.equals("F") || s.equals("f")) return "1111";
        return s;
    }
}
