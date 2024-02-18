package Labs.Lab_10.guiframe;

import Labs.Lab_10.tours.*;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class MainFrame extends JFrame {

    /*--- Code by hands:*/

    protected java.io.File fileData = new java.io.File("Tours.dat");

    final boolean chooseFile() {
        JFileChooser file = new JFileChooser();
        file.setMultiSelectionEnabled(false);
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Data files", "dat");
        file.setFileFilter(filter);
        file.setSelectedFile(fileData);
        if (file.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            fileData = file.getSelectedFile();
            Commands.setFile(fileData);
            return true;
        }
        return false;
    }

    final Tour getTour() {
        Tour tour = new Tour();
        tour.setTourName(tourNameText.getText().trim());
        tour.setName(nameText.getText().trim());
        tour.setDayAmount(tourDayAmountText.getText().trim());
        tour.setDayCost(tourDayCostText.getText().trim());
        tour.setTransportPrice(this.tourTransportPriceText.getText().trim());
        tour.setTripPrice(tourTripPriceText.getText().trim());
        return tour;
    }

    final void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, this.getTitle() + ": error",
                JOptionPane.ERROR_MESSAGE);
    }

    final void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg, this.getTitle() + ": error",
                JOptionPane.INFORMATION_MESSAGE);
    }

    static final String ABOUT_TEXT = "Written by Stanislau Senkevich";
    static final String STATUS_TEXT_DEFAULT = "Enter Alt+x to exit...";
    static final String STATUS_TEXT_FILE_OPEN = "Choose a file to work with...";
    static final String STATUS_TEXT_FILE_EXIT = "Exit application...";
    static final String STATUS_TEXT_HELP_ABOUT = "Show information about the application...";
    static final String STATUS_TEXT_COMMAND_ADD = "Add a new tour...";
    static final String STATUS_TEXT_COMMAND_REMOVE = "Remove existing tour by key...";
    static final String STATUS_TEXT_COMMAND_SHOW = "Show all tours...";
    static final String STATUS_TEXT_COMMAND_SHOW_SORTED = "Show all tours sorted by key...";
    static final String STATUS_TEXT_COMMAND_FIND = "Find and show tours by key...";

    final void setStatusTextDefault() {
        statusBarText.setText(STATUS_TEXT_DEFAULT);
        statusBarText.repaint();
    }

    static final int ROW_ISBN = 0;
    static final int ROW_AUTHOR = 1;
    static final int ROW_NAME = 2;
    
    static final Object[] TABLE_HEADER = {
        Tour.P_tourName, Tour.P_name, Tour.P_dayCost, Tour.P_dayAmount,
            Tour.P_transportPrice, Tour.P_price
    };
    static final int[] TABLE_SIZE = {
        150, 250, 100, 100, 100, 100
    };

    final DefaultTableModel createTableModel() {
        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        tm.setColumnIdentifiers(TABLE_HEADER);
        return tm;
    }

    final void fillTable(JTable tbl, List<String> src) {
        int i, n;
        DefaultTableModel tm = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };
        if (src != null) {
            tm.setColumnIdentifiers(TABLE_HEADER);
            for (i = 0, n = src.size(); i < n; i++) {
                Object[] rows = src.get(i).split(Tour.AREA_DEL);
                if (rows.length != TABLE_HEADER.length) {
                    showError("Invalid data at position " + i);
                    continue;
                }
                tm.addRow(rows);
            }
        }
        tbl.setModel(tm);
        if (src != null) {
            TableColumnModel cm = tbl.getColumnModel();
            for (i = 0; i < TABLE_SIZE.length; i++) {
                cm.getColumn(i).setPreferredWidth(TABLE_SIZE[i]);
            }
            tbl.setEnabled(true);
            tbl.setVisible(true);
        } else {
            tbl.setEnabled(false);
            tbl.setVisible(false);
        }
    }

    static final String[][] TEST_DATA = {

    };

    final List<String> getTestData() {
        ArrayList lst = new ArrayList(TEST_DATA.length);
        for (int i = 0; i < TEST_DATA.length; i++) {
            String[] r = TEST_DATA[i];
            String str = r[0];
            for (int j = 1; j < r.length; j++) {
                str += Tour.AREA_DEL + r[j];
            }
            lst.add(i, str);
        }
        return lst;
    }

    final void fillTable(JTable tbl) {
        fillTable(tbl, getTestData());
    }

    final void clearTable(JTable tbl) {
        fillTable(tbl, null);
    }

    private boolean chooseKeyDialogTargetRemove = false;

    static class ViewOptions {

        static enum Command {
            None,
            Show,
            ShowSorted,
            Find
        };
        static Command what;

        // Params for all commands:
        static String keyType;
        static String keyValue;
        static int comp; //0 -==, 1 -<, 2 ->
        static boolean reverse;
        static String delKeyType;
        static String delKeyValue;
    };

    static final String RESULT_TEXT_NONE = " ";
    static final String RESULT_TEXT_SHOW = "All tours, unordered:";
    static final String RESULT_TEXT_SHOW_SORTED = "All tours, ordered by ";
    static final String RESULT_TEXT_SHOW_REVERSE_SORTED = "All tours, reverse ordered by ";
    static final String RESULT_TEXT_FIND = "Find tour(s) by ";
    
    final void setOptions(ViewOptions.Command cmd) {

        String str, val;
        switch (cmd) {
            case None:
                ViewOptions.keyType = null;
                ViewOptions.keyValue = null;
                ViewOptions.comp = 0;
                ViewOptions.reverse = false;
                resultLabel.setText(RESULT_TEXT_NONE);
                break;
            case Show:
                ViewOptions.keyType = null;
                ViewOptions.keyValue = null;
                ViewOptions.comp = 0;
                ViewOptions.reverse = false;
                resultLabel.setText(RESULT_TEXT_SHOW);
                break;
            case ShowSorted:
                ViewOptions.keyType = sortedKeyComboBox.getItemAt(sortedKeyComboBox.getSelectedIndex());
                ViewOptions.reverse = sortedReverseCheckBox.isSelected();
                str = ViewOptions.reverse ? RESULT_TEXT_SHOW_REVERSE_SORTED : RESULT_TEXT_SHOW_SORTED;
                resultLabel.setText(str + ViewOptions.keyType + ":");
                break;
            case Find:
                str = chooseKeyTypeComboBox.getItemAt(
                        chooseKeyTypeComboBox.getSelectedIndex());
                val = chooseKeyValueField.getText();
                if (chooseKeyDialogTargetRemove) {
                    ViewOptions.delKeyType = str;
                    ViewOptions.delKeyValue = val;
                    // do not change ViewOptions.what
                    return;
                }
                ViewOptions.keyType = str;
                ViewOptions.keyValue = val;
                ViewOptions.comp = chooseKeyCompComboBox.getSelectedIndex();
                str = chooseKeyCompComboBox.getItemAt(ViewOptions.comp);
                resultLabel.setText(RESULT_TEXT_FIND + ViewOptions.keyType + str + val + ":");
                break;
        }       
        resultLabel.repaint();
        ViewOptions.what = cmd;
    }
    //--
    //-- View Commands:
    boolean viewLast(JDialog dlg) {
        switch (ViewOptions.what) {
            case None:
            default:
                return false;
            case Show:
                return viewShow(dlg);
            case ShowSorted:
                return viewShowSorted(dlg);
            case Find:
                return viewFind(dlg);
        }       
    }
    
    void viewSetCursor(JDialog dlg, Cursor cur) {
        if (dlg == null) {
            setCursor(cur);
        }
        else {
            dlg.setCursor(cur);
        }
    }
    
    boolean viewShow(JDialog dlg) {
        boolean isError = false;
        String errorMessage = null;
        viewSetCursor(dlg, Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        {
            try {
                List<String> result = Commands.readFile();
                fillTable(viewTable, result);
            } catch (Error | Exception e) {
                isError = true;
                errorMessage = e.getMessage();
            }
        }
        viewSetCursor(dlg, Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (isError) {
            showError(errorMessage);
            setOptions(ViewOptions.Command.None);
        }  
        return isError;
    }
    
    boolean viewShowSorted(JDialog dlg) {        
        boolean isError = false;
        String errorMessage = null;
        viewSetCursor(dlg, Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        {
            try {
                clearTable(viewTable);
                List<String> result = Commands.readFile(ViewOptions.keyType, ViewOptions.reverse);
                fillTable(viewTable, result);
            } catch (Error | Exception e) {
                isError = true;
                errorMessage = e.getMessage();
            }
        }
        viewSetCursor(dlg, Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (isError) {
            showError(errorMessage);
            setOptions(ViewOptions.Command.None);
        }
        return isError;
    }
    
    boolean viewFind(JDialog dlg) {
        boolean isError = false;
        String errorMessage = null;
        viewSetCursor(dlg, Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        {
            try {
                clearTable(viewTable);
                if (chooseKeyDialogTargetRemove) {
                    Commands.deleteFile(ViewOptions.delKeyType, ViewOptions.delKeyValue);
                } else {
                    List<String> result = (ViewOptions.comp == 0)
                             ? Commands.findByKey(ViewOptions.keyType, ViewOptions.keyValue)
                             : Commands.findByKey(ViewOptions.keyType, ViewOptions.keyValue, ViewOptions.comp);
                    fillTable(viewTable, result);
                }
            } catch (Error | Exception e) {
                isError = true;
                errorMessage = e.getMessage();
            }
        }
        viewSetCursor(dlg, Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (isError) {
            showError(errorMessage);
            setOptions(ViewOptions.Command.None);
        }
        return isError;
    }
    
    boolean viewAdd(JDialog dlg) {
        boolean isError = false;
        String errorMessage = null;
        viewSetCursor(dlg, Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        {
            try {
                clearTable(viewTable);
                Tour tour = getTour();
                Commands.appendFile(true, tour);
            } catch (Error | Exception e) {
                isError = true;
                errorMessage = e.getMessage();
            }
        }
        viewSetCursor(dlg, Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        if (isError) {
            showError(errorMessage);
        }
        return isError;
    }
    
    //--
    /*---*/

    public MainFrame() {
        initComponents();
        // Code by hands:
        Commands.setFile(fileData);
        setLocationRelativeTo(null);
        statusBar.setFloatable(false);
        statusBarText.setText(STATUS_TEXT_DEFAULT);
        clearTable(viewTable);
        setOptions(ViewOptions.Command.None);
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tourDialog = new JDialog();
        tourNameLabel = new JLabel();
        tourNameText = new JTextField();
        tourTripPriceLabel = new JLabel();
        tourTripPriceText = new JTextField();
        nameLabel = new JLabel();
        nameText = new JTextField();
        tourDayAmountLabel = new JLabel();
        tourDayAmountText = new JTextField();
        tourDayCostLabel = new JLabel();
        tourDayCostText = new JTextField();
        tourTransportPriceLabel = new JLabel();
        tourSeparator = new JSeparator();
        tourTransportPriceText = new JTextField();
//        tourAnnoLable = new javax.swing.JLabel();
//        tourAnnoScroll = new javax.swing.JScrollPane();
//        tourAnnoArea = new javax.swing.JTextArea();
        tourOK = new JButton();
        tourClose = new JButton();
        sortedDialog = new JDialog();
        sortedLabelTitle = new JLabel();
        sortedKeyComboBox = new JComboBox<>();
        sortedReverseCheckBox = new JCheckBox();
        jSeparator4 = new JSeparator();
        sortedButtonOK = new JButton();
        sortedButtonCancel = new JButton();
        chooseKeyDialog = new JDialog();
        chooseKeyLabelTitle = new JLabel();
        chooseKeyTypeLabel = new JLabel();
        chooseKeyTypeComboBox = new JComboBox<>();
        chooseKeyValueLabel = new JLabel();
        chooseKeyValueField = new JTextField();
        chooseKeyCompLabel = new JLabel();
        chooseKeyCompComboBox = new JComboBox<>();
        jSeparator5 = new JSeparator();
        chooseKeyOK = new JButton();
        chooseKeyCancel = new JButton();
        statusBar = new JToolBar();
        statusBarText = new JLabel();
        viewPane = new JScrollPane();
        viewTable = new JTable();
        resultLabel = new JLabel();
        mainMenuBar = new JMenuBar();
        menuFile = new JMenu();
        menuFileOpen = new JMenuItem();
        jSeparator1 = new JPopupMenu.Separator();
        menuFileExit = new JMenuItem();
        menuCommand = new JMenu();
        menuCommandAddTour = new JMenuItem();
        menuCommandRemove = new JMenuItem();
        jSeparator2 = new JPopupMenu.Separator();
        menuCommandShowTours = new JMenuItem();
        menuCommandShowSorted = new JMenuItem();
        jSeparator3 = new JPopupMenu.Separator();
        menuCommandFind = new JMenuItem();
        menuHelp = new JMenu();
        menuHelpAbout = new JMenuItem();

        tourDialog.setTitle("Add new tour");
        tourDialog.setAlwaysOnTop(true);
        tourDialog.setMinimumSize(new Dimension(420, 470));
        tourDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);

        tourNameLabel.setText("Tour name: ");

        nameLabel.setText("Name:");

        tourDayAmountLabel.setText("Day amount: ");

        tourDayCostLabel.setText("Day cost:");

        tourTransportPriceLabel.setText("Transport price:");

        tourTripPriceLabel.setText("Tour price:");

//        tourAnnoLable.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
//        tourAnnoLable.setText("Annotation:");
//
//        tourAnnoArea.setColumns(20);
//        tourAnnoArea.setRows(5);
//        tourAnnoScroll.setViewportView(tourAnnoArea);

        tourOK.setMnemonic('d');
        tourOK.setText("Add");
        tourOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tourOKActionPerformed(evt);
            }
        });

        tourClose.setMnemonic('c');
        tourClose.setText("Close");
        tourClose.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tourCloseActionPerformed(evt);
            }
        });

        GroupLayout tourDialogLayout = new GroupLayout(tourDialog.getContentPane());
        tourDialog.getContentPane().setLayout(tourDialogLayout);
        tourDialogLayout.setHorizontalGroup(
            tourDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(tourDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    //.addComponent(tourAnnoScroll)
                    .addComponent(tourSeparator, GroupLayout.Alignment.TRAILING)
                    .addGroup(tourDialogLayout.createSequentialGroup()
                        .addGap(100, 258, Short.MAX_VALUE)
                        .addComponent(tourOK)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(tourClose))
                    .addGroup(GroupLayout.Alignment.TRAILING, tourDialogLayout.createSequentialGroup()
                        .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(tourDayCostLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
                            .addComponent(tourDayAmountLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(nameLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tourTripPriceLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tourNameLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(tourTransportPriceLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(tourTripPriceText)
                            .addComponent(nameText)
                            .addComponent(tourDayAmountText)
                            .addComponent(tourDayCostText)
                            .addComponent(tourTransportPriceText, GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                            .addComponent(tourNameText, GroupLayout.Alignment.TRAILING))))));
                    //.addComponent(tourAnnoLable, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                //.addContainerGap())
        tourDialogLayout.setVerticalGroup(
            tourDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(tourDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tourNameLabel)
                    .addComponent(tourNameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tourTripPriceLabel)
                    .addComponent(tourTripPriceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(nameLabel)
                    .addComponent(nameText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tourDayAmountLabel)
                    .addComponent(tourDayAmountText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tourDayCostLabel)
                    .addComponent(tourDayCostText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tourTransportPriceText, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(tourTransportPriceLabel))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tourSeparator, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                //.addComponent(tourAnnoLable)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                //.addComponent(tourAnnoScroll, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(tourDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(tourClose)
                    .addComponent(tourOK)))
        );

        sortedDialog.setTitle("Show sorted");
        sortedDialog.setAlwaysOnTop(true);
        sortedDialog.setMaximumSize(new Dimension(340, 160));
        sortedDialog.setMinimumSize(new Dimension(340, 160));
        sortedDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);
        sortedDialog.setPreferredSize(new Dimension(340, 160));

        sortedLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        sortedLabelTitle.setText("Choose a key:");

        sortedKeyComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Tour name", "Name", "Day amount" }));

        sortedReverseCheckBox.setMnemonic('r');
        sortedReverseCheckBox.setText("Reverse");

        sortedButtonOK.setText("OK");
        sortedButtonOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortedButtonOKActionPerformed(evt);
            }
        });

        sortedButtonCancel.setText("Cancel");
        sortedButtonCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sortedButtonCancelActionPerformed(evt);
            }
        });

        GroupLayout sortedDialogLayout = new GroupLayout(sortedDialog.getContentPane());
        sortedDialog.getContentPane().setLayout(sortedDialogLayout);
        sortedDialogLayout.setHorizontalGroup(
            sortedDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sortedDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sortedDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(sortedLabelTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(GroupLayout.Alignment.TRAILING, sortedDialogLayout.createSequentialGroup()
                        .addComponent(sortedButtonOK)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(sortedButtonCancel, GroupLayout.PREFERRED_SIZE, 74, GroupLayout.PREFERRED_SIZE))
                    .addComponent(jSeparator4)
                    .addGroup(GroupLayout.Alignment.TRAILING, sortedDialogLayout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(sortedKeyComboBox, 0, 96, Short.MAX_VALUE)
                        .addGap(106, 106, 106)
                        .addComponent(sortedReverseCheckBox, GroupLayout.DEFAULT_SIZE, 92, Short.MAX_VALUE)
                        .addGap(18, 18, 18)))
                .addContainerGap())
        );
        sortedDialogLayout.setVerticalGroup(
            sortedDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(sortedDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(sortedLabelTitle)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sortedDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sortedKeyComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(sortedReverseCheckBox))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
                .addComponent(jSeparator4, GroupLayout.PREFERRED_SIZE, 10, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(sortedDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(sortedButtonCancel)
                    .addComponent(sortedButtonOK))
                .addGap(12, 12, 12))
        );

        chooseKeyDialog.setAlwaysOnTop(true);
        chooseKeyDialog.setMinimumSize(new Dimension(320, 230));
        chooseKeyDialog.setModalityType(Dialog.ModalityType.DOCUMENT_MODAL);

        chooseKeyLabelTitle.setHorizontalAlignment(SwingConstants.CENTER);
        chooseKeyLabelTitle.setText("Choose a key:");

        chooseKeyTypeLabel.setHorizontalAlignment(SwingConstants.LEFT);
        chooseKeyTypeLabel.setLabelFor(chooseKeyTypeComboBox);
        chooseKeyTypeLabel.setText("Key type:");

        chooseKeyTypeComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "Tour name", "Name", "Day amount" }));

        chooseKeyValueLabel.setHorizontalAlignment(SwingConstants.LEFT);
        chooseKeyValueLabel.setLabelFor(chooseKeyValueField);
        chooseKeyValueLabel.setText("Key value:");

        chooseKeyValueField.setHorizontalAlignment(JTextField.LEFT);

        chooseKeyCompLabel.setHorizontalAlignment(SwingConstants.LEFT);
        chooseKeyCompLabel.setLabelFor(chooseKeyCompComboBox);
        chooseKeyCompLabel.setText("Comparision type:");

        chooseKeyCompComboBox.setModel(new DefaultComboBoxModel<>(new String[] { "==", ">", "<" }));

        chooseKeyOK.setText("OK");
        chooseKeyOK.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseKeyOKActionPerformed(evt);
            }
        });

        chooseKeyCancel.setText("Cancel");
        chooseKeyCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chooseKeyCancelActionPerformed(evt);
            }
        });

        GroupLayout chooseKeyDialogLayout = new GroupLayout(chooseKeyDialog.getContentPane());
        chooseKeyDialog.getContentPane().setLayout(chooseKeyDialogLayout);
        chooseKeyDialogLayout.setHorizontalGroup(
            chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(chooseKeyDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addGroup(chooseKeyDialogLayout.createSequentialGroup()
                        .addGroup(chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.TRAILING, false)
                            .addComponent(chooseKeyValueLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chooseKeyCompLabel, GroupLayout.Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addComponent(chooseKeyTypeLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(12, 12, 12)
                        .addGroup(chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
                            .addComponent(chooseKeyValueField)
                            .addComponent(chooseKeyCompComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(chooseKeyTypeComboBox, GroupLayout.Alignment.TRAILING, 0, 126, Short.MAX_VALUE)))
                    .addGroup(GroupLayout.Alignment.TRAILING, chooseKeyDialogLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(chooseKeyOK)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(chooseKeyCancel))
                    .addComponent(jSeparator5)
                    .addComponent(chooseKeyLabelTitle, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        chooseKeyDialogLayout.setVerticalGroup(
            chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(chooseKeyDialogLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chooseKeyLabelTitle)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(chooseKeyTypeLabel)
                    .addComponent(chooseKeyTypeComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(chooseKeyValueLabel)
                    .addComponent(chooseKeyValueField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(chooseKeyCompComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                    .addComponent(chooseKeyCompLabel))
                .addGap(13, 13, 13)
                .addComponent(jSeparator5, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                .addGroup(chooseKeyDialogLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(chooseKeyCancel)
                    .addComponent(chooseKeyOK))
                .addContainerGap())
        );

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("GUI Frame Application");
        setName("mainFrame"); // NOI18N
        setResizable(false);

        statusBar.setRollover(true);

        statusBarText.setHorizontalAlignment(SwingConstants.LEFT);
        statusBarText.setText("...");
        statusBar.add(statusBarText);

        viewPane.setBorder(BorderFactory.createEmptyBorder(1, 1, 1, 1));
        viewPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
        viewPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        viewTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        viewTable.setEnabled(false);
        viewTable.getTableHeader().setResizingAllowed(false);
        viewTable.getTableHeader().setReorderingAllowed(false);
        viewPane.setViewportView(viewTable);

        resultLabel.setHorizontalAlignment(SwingConstants.CENTER);
        resultLabel.setLabelFor(viewPane);
        resultLabel.setText("No results");
        resultLabel.setToolTipText("");
        resultLabel.setBorder(BorderFactory.createEtchedBorder(javax.swing.border.EtchedBorder.RAISED));

        menuFile.setMnemonic('f');
        menuFile.setText("File");

        menuFileOpen.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_O, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuFileOpen.setMnemonic('o');
        menuFileOpen.setText("Open");
        menuFileOpen.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuFileOpenMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuFileOpenMouseExited(evt);
            }
        });
        menuFileOpen.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileOpenActionPerformed(evt);
            }
        });
        menuFile.add(menuFileOpen);
        menuFile.add(jSeparator1);

        menuFileExit.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_X, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuFileExit.setMnemonic('x');
        menuFileExit.setText("Exit");
        menuFileExit.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuFileExitMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuFileExitMouseExited(evt);
            }
        });
        menuFileExit.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuFileExitActionPerformed(evt);
            }
        });
        menuFile.add(menuFileExit);

        mainMenuBar.add(menuFile);

        menuCommand.setMnemonic('c');
        menuCommand.setText("Command");

        menuCommandAddTour.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_A, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCommandAddTour.setMnemonic('a');
        menuCommandAddTour.setText("Add tour");
        menuCommandAddTour.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuCommandAddTourMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuCommandAddTourMouseExited(evt);
            }
        });
        menuCommandAddTour.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCommandAddTourActionPerformed(evt);
            }
        });
        menuCommand.add(menuCommandAddTour);

        menuCommandRemove.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_R, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCommandRemove.setMnemonic('r');
        menuCommandRemove.setText("Remove tour");
        menuCommandRemove.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuCommandRemoveMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuCommandRemoveMouseExited(evt);
            }
        });
        menuCommandRemove.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCommandRemoveActionPerformed(evt);
            }
        });
        menuCommand.add(menuCommandRemove);
        menuCommand.add(jSeparator2);

        menuCommandShowTours.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_S, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCommandShowTours.setMnemonic('s');
        menuCommandShowTours.setText("Show");
        menuCommandShowTours.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuCommandShowToursMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuCommandShowToursMouseExited(evt);
            }
        });
        menuCommandShowTours.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCommandShowToursActionPerformed(evt);
            }
        });
        menuCommand.add(menuCommandShowTours);

        menuCommandShowSorted.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_H, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCommandShowSorted.setMnemonic('h');
        menuCommandShowSorted.setText("Show sorted");
        menuCommandShowSorted.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuCommandShowSortedMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuCommandShowSortedMouseExited(evt);
            }
        });
        menuCommandShowSorted.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCommandShowSortedActionPerformed(evt);
            }
        });
        menuCommand.add(menuCommandShowSorted);
        menuCommand.add(jSeparator3);

        menuCommandFind.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuCommandFind.setMnemonic('f');
        menuCommandFind.setText("Find tour");
        menuCommandFind.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuCommandFindMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuCommandFindMouseExited(evt);
            }
        });
        menuCommandFind.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuCommandFindActionPerformed(evt);
            }
        });
        menuCommand.add(menuCommandFind);

        mainMenuBar.add(menuCommand);

        menuHelp.setMnemonic('h');
        menuHelp.setText("Help");

        menuHelpAbout.setAccelerator(KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_B, java.awt.event.InputEvent.ALT_DOWN_MASK));
        menuHelpAbout.setMnemonic('b');
        menuHelpAbout.setText("About");
        menuHelpAbout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                menuHelpAboutMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                menuHelpAboutMouseExited(evt);
            }
        });
        menuHelpAbout.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuHelpAboutActionPerformed(evt);
            }
        });
        menuHelp.add(menuHelpAbout);

        mainMenuBar.add(menuHelp);

        setJMenuBar(mainMenuBar);

        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING, false)
                    .addComponent(viewPane, GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                    .addComponent(statusBar, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultLabel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(resultLabel)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(viewPane, GroupLayout.PREFERRED_SIZE, 317, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusBar, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
                .addGap(3, 3, 3))
        );

        statusBar.getAccessibleContext().setAccessibleDescription("");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void menuFileExitActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileExitActionPerformed
        setStatusTextDefault();
        System.exit(0);
    }//GEN-LAST:event_menuFileExitActionPerformed

    private void menuFileOpenActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuFileOpenActionPerformed
        setStatusTextDefault();
        if (chooseFile()) {
            viewLast(null);
        }
    }//GEN-LAST:event_menuFileOpenActionPerformed

    private void menuFileOpenMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileOpenMouseEntered
        statusBarText.setText(STATUS_TEXT_FILE_OPEN);
        statusBarText.repaint();
    }//GEN-LAST:event_menuFileOpenMouseEntered

    private void menuFileOpenMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileOpenMouseExited
        setStatusTextDefault();
    }//GEN-LAST:event_menuFileOpenMouseExited

    private void menuFileExitMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileExitMouseEntered
        statusBarText.setText(STATUS_TEXT_FILE_EXIT);
        statusBarText.repaint();
    }//GEN-LAST:event_menuFileExitMouseEntered

    private void menuFileExitMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuFileExitMouseExited
        setStatusTextDefault();
    }//GEN-LAST:event_menuFileExitMouseExited

    private void menuHelpAboutMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuHelpAboutMouseEntered
        statusBarText.setText(STATUS_TEXT_HELP_ABOUT);
        statusBarText.repaint();
    }//GEN-LAST:event_menuHelpAboutMouseEntered

    private void menuHelpAboutMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuHelpAboutMouseExited
        setStatusTextDefault();
    }//GEN-LAST:event_menuHelpAboutMouseExited

    private void menuHelpAboutActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuHelpAboutActionPerformed
        setStatusTextDefault();
        JOptionPane.showMessageDialog(this, ABOUT_TEXT, this.getTitle(),
                JOptionPane.INFORMATION_MESSAGE);
    }//GEN-LAST:event_menuHelpAboutActionPerformed

    private void menuCommandAddTourMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandAddBookMouseEntered
        statusBarText.setText(STATUS_TEXT_COMMAND_ADD);
        statusBarText.repaint();
    }//GEN-LAST:event_menuCommandAddBookMouseEntered

    private void menuCommandAddTourMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandAddBookMouseExited
        setStatusTextDefault();
    }//GEN-LAST:event_menuCommandAddBookMouseExited

    private void menuCommandAddTourActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCommandAddBookActionPerformed
        setStatusTextDefault();
        tourDialog.setLocationRelativeTo(this);
        tourDialog.setVisible(true);
    }//GEN-LAST:event_menuCommandAddTourActionPerformed

    private void tourOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookOKActionPerformed

        boolean isError = viewAdd(tourDialog);
        tourDialog.setVisible(isError);
        if (!isError) {
            viewLast(null);
        }
    }//GEN-LAST:event_tourOKActionPerformed

    private void tourCloseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bookCloseActionPerformed
        tourDialog.setVisible(false);
    }//GEN-LAST:event_tourCloseActionPerformed

    private void menuCommandShowToursActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCommandShowBooksActionPerformed
        setStatusTextDefault();
        setOptions(ViewOptions.Command.Show);
        viewShow(null);
    }//GEN-LAST:event_menuCommandShowBooksActionPerformed

    private void menuCommandShowToursMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandShowBooksMouseEntered
        statusBarText.setText(STATUS_TEXT_COMMAND_SHOW);
        statusBarText.repaint();
    }//GEN-LAST:event_menuCommandShowToursMouseEntered

    private void menuCommandShowToursMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandShowBooksMouseExited
        setStatusTextDefault();
    }//GEN-LAST:event_menuCommandShowToursMouseExited

    private void menuCommandShowSortedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCommandShowSortedActionPerformed
        setStatusTextDefault();
        sortedDialog.setLocationRelativeTo(this);
        sortedDialog.setVisible(true);
    }//GEN-LAST:event_menuCommandShowSortedActionPerformed

    private void menuCommandShowSortedMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandShowSortedMouseEntered
        statusBarText.setText(STATUS_TEXT_COMMAND_SHOW_SORTED);
        statusBarText.repaint();
    }//GEN-LAST:event_menuCommandShowSortedMouseEntered

    private void menuCommandShowSortedMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandShowSortedMouseExited
        setStatusTextDefault();
    }//GEN-LAST:event_menuCommandShowSortedMouseExited

    private void menuCommandRemoveActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCommandRemoveActionPerformed
        setStatusTextDefault();
        chooseKeyDialogTargetRemove = true;
        chooseKeyDialog.setTitle("Remove tour");
        chooseKeyDialog.setLocationRelativeTo(this);
        chooseKeyCompLabel.setEnabled(false);
        chooseKeyCompComboBox.setEnabled(false);
        chooseKeyDialog.setVisible(true);
    }//GEN-LAST:event_menuCommandRemoveActionPerformed

    private void menuCommandRemoveMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandRemoveMouseEntered
        statusBarText.setText(STATUS_TEXT_COMMAND_REMOVE);
        statusBarText.repaint();
    }//GEN-LAST:event_menuCommandRemoveMouseEntered

    private void menuCommandRemoveMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandRemoveMouseExited
        setStatusTextDefault();
    }//GEN-LAST:event_menuCommandRemoveMouseExited

    private void menuCommandFindActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuCommandFindActionPerformed
        setStatusTextDefault();
        chooseKeyDialogTargetRemove = false;
        chooseKeyDialog.setTitle("Find tour");
        chooseKeyDialog.setLocationRelativeTo(this);
        chooseKeyCompLabel.setEnabled(true);
        chooseKeyCompComboBox.setEnabled(true);
        chooseKeyDialog.setVisible(true);
    }//GEN-LAST:event_menuCommandFindActionPerformed

    private void menuCommandFindMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandFindMouseEntered
        statusBarText.setText(STATUS_TEXT_COMMAND_FIND);
        statusBarText.repaint();
    }//GEN-LAST:event_menuCommandFindMouseEntered

    private void menuCommandFindMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_menuCommandFindMouseExited
        setStatusTextDefault();
    }//GEN-LAST:event_menuCommandFindMouseExited

    private void sortedButtonOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortedButtonOKActionPerformed
        setOptions(ViewOptions.Command.ShowSorted);
        viewShowSorted(sortedDialog);
        sortedDialog.setVisible(false);
    }//GEN-LAST:event_sortedButtonOKActionPerformed

    private void sortedButtonCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sortedButtonCancelActionPerformed
        sortedDialog.setVisible(false);
    }//GEN-LAST:event_sortedButtonCancelActionPerformed

    private void chooseKeyOKActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseKeyOKActionPerformed
        setOptions(ViewOptions.Command.Find);
        boolean isError = viewFind(chooseKeyDialog);
        if (chooseKeyDialogTargetRemove) {
            chooseKeyDialog.setVisible(isError);
            if (!isError) {
                viewLast(null);
            }
        }
        else {
            chooseKeyDialog.setVisible(false);
        }
    }//GEN-LAST:event_chooseKeyOKActionPerformed

    private void chooseKeyCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chooseKeyCancelActionPerformed
        chooseKeyDialog.setVisible(false);
    }//GEN-LAST:event_chooseKeyCancelActionPerformed

    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MainFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JLabel tourTripPriceLabel;
    private JTextField tourTripPriceText;
    private JButton tourClose;
    private JDialog tourDialog;
    private JLabel tourNameLabel;
    private JTextField tourNameText;
    private JLabel nameLabel;
    private JTextField nameText;
    private JButton tourOK;
    private JLabel tourTransportPriceLabel;
    private JTextField tourTransportPriceText;
    private JLabel tourDayCostLabel;
    private JTextField tourDayCostText;
    private JSeparator tourSeparator;
    private JLabel tourDayAmountLabel;
    private JTextField tourDayAmountText;
    private JButton chooseKeyCancel;
    private JComboBox<String> chooseKeyCompComboBox;
    private JLabel chooseKeyCompLabel;
    private JDialog chooseKeyDialog;
    private JLabel chooseKeyLabelTitle;
    private JButton chooseKeyOK;
    private JComboBox<String> chooseKeyTypeComboBox;
    private JLabel chooseKeyTypeLabel;
    private JTextField chooseKeyValueField;
    private JLabel chooseKeyValueLabel;
    private JPopupMenu.Separator jSeparator1;
    private JPopupMenu.Separator jSeparator2;
    private JPopupMenu.Separator jSeparator3;
    private JSeparator jSeparator4;
    private JSeparator jSeparator5;
    private JMenuBar mainMenuBar;
    private JMenu menuCommand;
    private JMenuItem menuCommandAddTour;
    private JMenuItem menuCommandFind;
    private JMenuItem menuCommandRemove;
    private JMenuItem menuCommandShowTours;
    private JMenuItem menuCommandShowSorted;
    private JMenu menuFile;
    private JMenuItem menuFileExit;
    private JMenuItem menuFileOpen;
    private JMenu menuHelp;
    private JMenuItem menuHelpAbout;
    private JLabel resultLabel;
    private JButton sortedButtonCancel;
    private JButton sortedButtonOK;
    private JDialog sortedDialog;
    private JComboBox<String> sortedKeyComboBox;
    private JLabel sortedLabelTitle;
    private JCheckBox sortedReverseCheckBox;
    private JToolBar statusBar;
    private JLabel statusBarText;
    private JScrollPane viewPane;
    private JTable viewTable;
    // End of variables declaration//GEN-END:variables
}
