package numericconverter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu {
    private JMenuBar menuBar = new JMenuBar();
    private JMenu file = new JMenu("Файл");
    private JMenu info = new JMenu("Справка");
    private JMenuItem about = new JMenuItem("О программе");
    private JMenu theme = new JMenu("Сменить оформление");
    private JMenuItem exit = new JMenuItem("Выход");
    private JFrame frame;

    Menu(JPanel panelForMenu, JFrame frame){
        initMenu();
        this.frame = frame;
        panelForMenu.add(menuBar, BorderLayout.NORTH);
    }

    public void initMenu(){
        file.add(theme);
        UIManager.LookAndFeelInfo[] lookAndFeels = UIManager.getInstalledLookAndFeels();
        for (UIManager.LookAndFeelInfo i : lookAndFeels){
            JMenuItem themeItem = new JMenuItem(i.getName());
            themeItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    try {
                        UIManager.setLookAndFeel(i.getClassName());
                        SwingUtilities.updateComponentTreeUI(frame);
                    } catch (ClassNotFoundException e1) {
                        e1.printStackTrace();
                    } catch (InstantiationException e1) {
                        e1.printStackTrace();
                    } catch (IllegalAccessException e1) {
                        e1.printStackTrace();
                    } catch (UnsupportedLookAndFeelException e1) {
                        e1.printStackTrace();
                    }
                }
            });
            theme.add(themeItem);
        }
        file.add(exit);
        info.add(about);
        menuBar.add(file);
        menuBar.add(info);
        menuListeners();
    }

    public void menuListeners(){
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int res = JOptionPane.showConfirmDialog(frame, "Вы действительно хотите выйти?", "Закрыть программу?", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (res == 0) {
                    frame.dispose();
                    System.exit(0);
                }
            }
        });

        about.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(frame, "Автор: Богдан Коломиец\nEmail: bogdan.kolomiiets@gmail.com\n" +
                        "Это моя третья программа на языке программирования java.\nЕсли она Вам нравиться - пользуйтесь на здоровье. ))\"",
                        "О программе", JOptionPane.INFORMATION_MESSAGE, null);
            }
        });
    }

}
