package org.example.gui;

import org.example.models.Nation;
import org.example.services.*;
import org.example.theme.StyledButtonUI;
import org.example.theme.Theme;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.util.concurrent.TimeUnit;

public class MainWindow {
    private JFrame frame;
    private JLabel milLabel;
    private JLabel distLabel;
    private Nation currentNation = Nation.ALIADO_EIXO;

    public void initialize() {
        createWindow();
        startProcessingThread();
    }

    private void createWindow() {
        // Configuração inicial da janela
        frame = new JFrame("ACSArty");

        // Ícone da área de trabalho e barra de tarefas
        frame.setIconImage(new ImageIcon(getClass().getResource("/icons/ACSArty.png")).getImage());

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 300);
        frame.setUndecorated(true);
        frame.setShape(new RoundRectangle2D.Double(0, 0, 500, 450, 15, 15));
        frame.setBackground(Theme.BACKGROUND_COLOR);
        frame.setAlwaysOnTop(true);
        frame.setFocusableWindowState(true);
        frame.setAutoRequestFocus(true);

        // Painel principal com borda arredondada e sombra
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Borda principal fina (1px) - ajuste estas cores conforme necessário
                g2d.setColor(Theme.WINDOW_BORDER_COLOR); // Cor da borda fina
                g2d.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 0, 0); // Borda retangular fina

                // Fundo da janela
                g2d.setColor(Theme.DARKER_BG);
                g2d.fillRect(1, 1, getWidth()-2, getHeight()-2); // Preenche o interior

                // Barra de título com cantos arredondados apenas no topo
                int titleBarHeight = 25;
                int cornerRadius = 1; // Raio do arredondamento

                // Cria um caminho para a barra de título
                Path2D path = new Path2D.Double();
                path.moveTo(1, 1 + cornerRadius); // Início do arredondamento esquerdo
                path.quadTo(1, 1, 1 + cornerRadius, 1); // Arredonda canto superior esquerdo
                path.lineTo(getWidth() - 1 - cornerRadius, 1); // Linha reta superior
                path.quadTo(getWidth() - 1, 1, getWidth() - 1, 1 + cornerRadius); // Arredonda canto superior direito
                path.lineTo(getWidth() - 1, 1 + titleBarHeight); // Linha reta lateral direita
                path.lineTo(1, 1 + titleBarHeight); // Linha reta inferior
                path.closePath(); // Fecha o caminho

                g2d.setColor(Theme.WINDOW_BORDER_COLOR);
                g2d.fill(path); // Preenche a barra de título

                // Adiciona uma borda fina na barra de título (opcional)
                g2d.setColor(Theme.WINDOW_BORDER_COLOR);
                g2d.draw(path);

                g2d.dispose();
            }
        };
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(new EmptyBorder(5, 5, 20, 10));
        mainPanel.setOpaque(false);

        // Painel de controles da janela (fechar, minimizar)
        JPanel controlsPanel = createWindowControls();
        mainPanel.add(controlsPanel);

        // Logo e título
        JPanel logoPanel = createLogoPanel();
        mainPanel.add(logoPanel);

        // Menu de nações (botões na horizontal)
        JPanel nationPanel = createNationSelector();
        mainPanel.add(nationPanel);

        // Painel de resultados (MIL e Distância)
        JPanel resultPanel = createResultPanel();
        mainPanel.add(resultPanel);

        // Movimentar janela pela barra de título
        MouseAdapter ma = new MouseAdapter() {
            private Point offset;

            public void mousePressed(MouseEvent e) {
                if (e.getY() < 50) {
                    offset = e.getPoint();
                }
            }

            public void mouseDragged(MouseEvent e) {
                if (offset != null) {
                    Point newLoc = frame.getLocation();
                    newLoc.x += e.getX() - offset.x;
                    newLoc.y += e.getY() - offset.y;
                    frame.setLocation(newLoc);
                }
            }
        };
        mainPanel.addMouseListener(ma);
        mainPanel.addMouseMotionListener(ma);

        frame.add(mainPanel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private JPanel createWindowControls() {
        // Painel de controles da janela
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT, -2, -2));
        panel.setOpaque(false);
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));

        // Botão de minimizar
        JButton minimizeButton = createControlButton("-");
        minimizeButton.addActionListener(e -> frame.setState(Frame.ICONIFIED));

        // Botão de maximizar desabilitado
        JButton maximizeButton = createControlButton("□");
        maximizeButton.setEnabled(false);
        maximizeButton.setForeground(Color.GRAY);

        // Botão de fechar
        JButton closeButton = createControlButton("×");
        closeButton.addActionListener(e -> System.exit(0));
        closeButton.setForeground(Theme.SECONDARY_COLOR);

        panel.add(minimizeButton);
        panel.add(maximizeButton);
        panel.add(closeButton);

        return panel;
    }

    private JButton createControlButton(String text) {
        // Estilo dos botões de controle da janela
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Theme.TEXT_COLOR);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 5));

        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) {
                button.setForeground(button.isEnabled() ? Theme.CLOSE_BTN : Color.GRAY);
            }
            public void mouseExited(MouseEvent e) {
                button.setForeground(button.isEnabled() ? Theme.TEXT_COLOR : Color.GRAY);
            }
        });

        return button;
    }

    private JPanel createLogoPanel() {
        // Painel do logo e título
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(5, 0, 5, 0));

        ImageIcon originalIcon = new ImageIcon("src/main/java/org/example/imagens/ACSArty.png");
        Image scaledImage = originalIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
        imageLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(imageLabel);

        JLabel title = new JLabel("CONVERSOR DE ARTILHARIA");
        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.PRIMARY_COLOR);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        return panel;
    }

    private JPanel createNationSelector() {
        // Painel de seleção de nações
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 5));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(2, 0, 2, 0));

        JLabel title = new JLabel("EXÉRCITO:");
        title.setFont(Theme.BUTTON_FONT);
        title.setForeground(Theme.PRIMARY_COLOR);
        panel.add(title);

        ButtonGroup group = new ButtonGroup();

        for (Nation nation : Nation.values()) {
            JToggleButton button = createNationButton(nation);
            group.add(button);
            panel.add(button);
        }

        return panel;
    }

    private JToggleButton createNationButton(Nation nation) {
        // Botões de seleção de nação
        JToggleButton button = new JToggleButton(nation.getName());
        button.setActionCommand(String.valueOf(nation.getValue()));
        button.setFont(Theme.BUTTON_FONT);
        button.setForeground(Theme.TEXT_COLOR);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(false);
        button.setPreferredSize(new Dimension(110, 25));

        button.setUI(new StyledButtonUI());

        if (nation == Nation.ALIADO_EIXO) {
            button.setSelected(true);
        }

        button.addActionListener(e -> {
            int nationValue = Integer.parseInt(e.getActionCommand());
            currentNation = Nation.values()[nationValue - 1];
        });

        return button;
    }

    private JPanel createResultPanel() {
        // Painel de resultados MIL e Distância
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(false);
        panel.setBorder(new EmptyBorder(2, 0, 2, 0));

        milLabel = new JLabel("MIL: --");
        milLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        milLabel.setForeground(Theme.ACCENT_COLOR);
        milLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(milLabel);

        distLabel = new JLabel("Distância: --");
        distLabel.setFont(new Font("Segoe UI", Font.BOLD, 22));
        distLabel.setForeground(Theme.SECONDARY_COLOR);
        distLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(distLabel);

        JLabel create_by = new JLabel("Criado por - Afanalbeto");
        create_by.setFont(Theme.CREATE_BY);
        create_by.setForeground(Theme.CREATE_BY_COLOR);
        create_by.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(create_by);

        return panel;
    }

    private void startProcessingThread() {
        new Thread(() -> {
            ScreenshotService screenshotService = new ScreenshotService();
            OCRService ocrService = new OCRService();
            CalculationService calculationService = new CalculationService();

            while (true) {
                try {
                    BufferedImage image = screenshotService.captureMilArea();
                    if (image == null) {
                        TimeUnit.MILLISECONDS.sleep(500);
                        continue;
                    }

                    String ocrResult = ocrService.processImage(image);
                    if (ocrResult != null) {
                        int milValue = Integer.parseInt(ocrResult);
                        double distance = calculationService.calculateDistance(milValue, currentNation);

                        SwingUtilities.invokeLater(() -> {
                            milLabel.setText("MIL: " + milValue);
                            distLabel.setText("Distância: " + (int) Math.round(distance));
                        });
                    }

                    TimeUnit.MILLISECONDS.sleep(180);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}