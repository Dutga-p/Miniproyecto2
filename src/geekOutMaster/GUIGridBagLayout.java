package geekOutMaster;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

public class GUIGridBagLayout extends JFrame{

    public  static final String CREDITOS="CRÉDITOS\n" +
            "David Camilo Ordoñez Marin";

    public  static final String AYUDA=" Al lanzar los dados 7 Dados pasan a ser los Dado Activos y 3 dados pasan a ser dados Inactivos\n" +
            " Al presionar los Dado se utilizan según las habilidades de sus caras y se pasan al sector de \"Dado Utilizados\"\n" +
            " Si al final hay uno o más dragones activos pierde todos los puntos. Si usa este dado también perderá todos los puntos de la ronda y los acumulados \n" +
            " Se ganará si logras sumar 30 puntos en 5 rondas consecutivas de juego. ";
    public static final String BOTON_EXPLICACION ="Bienvenido a GreekOutMaster"
            + "\nTienes máximo 5 rondas para sumar 30 puntos y ganar "
            + "\nEl Meeple permite relanzar otro dado en juego, es decir, de la sección dados activos"
            + "\nLa Nave Espacial envía un dado no usado (de la sección dados activos) a la sección de dados inactivos."
            + "\nEl Superhéroe permite que cualquier dado no usado (sección dados activos) sea volteado y colocado en su cara opuesta."
            + "\nEl corazón permite tomar un dado de la sección de dados inactivos y lanzarlo para que sea un nuevo dado activo."
            + "\nEl Dragón es la cara que se quiere evitar, ya que si al final de la ronda es el último dado activo que queda se habrán perdido todos los puntos ganados y acumulados."
            + "\n42 es la cara que permite sumar puntos al final de la ronda";


    private Header headerProject;
    private JLabel textoPuntaje, textoPuntajeTotal, textoRonda;
    private JButton lanzar, ayuda, creditos, botonExplicacion, continuarReiniciar;
    private JPanel panelDadoActivos, panelDadoUtilizados, panelDadoInactivos, panelPuntaje, panelRonda;
    private ImageIcon imageDado;
    private JTextArea mensajesSalida;
    private Escucha escucha;
    private CambiarImagen cambiarImagen;
    private AccionSuperHeroe superheroe;
    private AccionCorazon corazon;
    private AccionCohete cohete;
    private Model Model;
    private ArrayList<JButton> botones;
    private ArrayList<JButton> botonesUtilizados;
    private ArrayList<JButton> botonesInactivos;
    private HashMap<String, JButton> valorBotones;
    private HashMap<JButton, String> botonANombre;
    private int nuevoEscucha = 0; // Dependiendo del numero usa un MouseListener distinto
    private int puntaje, puntajeRonda;
    private int ronda;
    private int dragon;
    private int estadoDelJuego; // 0 si sigue tirando Dado, 1 si ya termino la ronda

    /**
     * Constructor de la clase GUI
     */
    public GUIGridBagLayout(){
        initGUI();

        //Configuración por defecto del JFrame
        this.setTitle("Game Greek Out Master");
        this.setSize(200,100);
        this.pack();
        this.setResizable(false);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    /**
     * Este método se utiliza para configurar la configuración predeterminada de JComponent,
     * crear objetos de escucha y control utilizados para la clase GUI
     */
    private void initGUI() {
        //Configurar el diseño del contenedor JFrame
        this.getContentPane().setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();

        //Crear objeto de escucha y objeto de control
        escucha = new Escucha();
        cambiarImagen = new CambiarImagen();
        superheroe = new AccionSuperHeroe();
        corazon = new AccionCorazon();
        cohete = new AccionCohete();
        Model = new Model();
        botones = new ArrayList<>();
        botonesUtilizados = new ArrayList<>();
        botonesInactivos = new ArrayList<>();
        valorBotones = new HashMap<>();
        botonANombre = new HashMap<>();
        puntaje = 0;
        puntajeRonda = 0;
        ronda = 1;
        dragon = 0; // 1 si se presiono el dragon, de lo contrario 0
        estadoDelJuego = 0;

        //Configurar JComponents

        /**
         * Creacion del titulo
         */
        headerProject = new Header("Geek Out Masters", Color.BLACK);
        constraints.gridx=3;
        constraints.gridy=1;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.CENTER;
        this.add(headerProject,constraints);

        /**
         * Creacion de boton "Ayuda"
         */
        ayuda = new JButton(" Ayuda");
        ayuda.addActionListener(escucha);
        ayuda.setContentAreaFilled(false);
        ayuda.setBorderPainted(false);
        ayuda.setOpaque(false);
        constraints.gridx=0;
        constraints.gridy=1;
        constraints.gridwidth=2;
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.CENTER;
        this.add(ayuda,constraints);

        /**
         * Creacion de boton "Créditos"
         */
        creditos = new JButton(" Créditos ");
        creditos.addActionListener(escucha);
        creditos.setContentAreaFilled(false);
        creditos.setBorderPainted(false);
        creditos.setOpaque(false);
        constraints.gridx=2;
        constraints.gridy=1;
        constraints.gridwidth=1;
        constraints.fill=GridBagConstraints.WEST;
        constraints.anchor=GridBagConstraints.LINE_START;
        this.add(creditos,constraints);


        /**
         * Creacion de boton "Explicación Dado"
         */
        botonExplicacion = new JButton("Explicación de los Poderes");
        botonExplicacion.addActionListener(escucha);
        botonExplicacion.setContentAreaFilled(false);
        botonExplicacion.setBorderPainted(false);
        botonExplicacion.setOpaque(false);
        constraints.gridx=7;
        constraints.gridy=1;
        constraints.gridwidth=1;
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.CENTER;
        this.add(botonExplicacion,constraints);

        // Puntaje ronda
        textoPuntaje = new JLabel();
        textoPuntaje.setHorizontalAlignment(SwingConstants.CENTER);

        // Puntaje total
        textoPuntajeTotal = new JLabel();
        textoPuntajeTotal.setHorizontalAlignment(SwingConstants.CENTER);

        // Ronda
        textoRonda = new JLabel();
        /**
         * Creacion de boton "tirar Dado"
         */
        lanzar = new JButton("Tirar los Dado");
        lanzar.addActionListener(escucha);
        lanzar.setBorderPainted(false);
        lanzar.setContentAreaFilled(false);
        lanzar.setOpaque(false);
        lanzar.setIcon(null);
        lanzar.setFocusPainted(false);
        lanzar.setRolloverEnabled(false);
        constraints.gridx=1;
        constraints.gridy= 3;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.CENTER;
        constraints.anchor=GridBagConstraints.CENTER;
        add(lanzar,constraints);

        /**
         * Creacion de panel Dado activos
         */
        panelDadoActivos = new JPanel();
        panelDadoActivos.setPreferredSize(new Dimension(400,250));
        panelDadoActivos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 5),
                "Dados Activos", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 25), Color.GREEN));
        panelDadoActivos.setBackground(new Color(255,255,255,0));
        panelDadoActivos.add(lanzar);
        constraints.gridx=0;
        constraints.gridy=2;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.CENTER;
        add(panelDadoActivos,constraints);

        /**
         * Creacion de panel Dado inactivos
         */
        panelDadoInactivos = new JPanel();
        panelDadoInactivos.setPreferredSize(new Dimension(400,250));
        panelDadoInactivos.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 5),
                "Dados Inactivos", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 25), Color.BLUE));
        panelDadoInactivos.setBackground(new Color(255,255,255,0));

        constraints.gridx=3;
        constraints.gridy=2;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.CENTER;
        add(panelDadoInactivos,constraints);

        /**
         * Creacion de panel Dado utilizados
         */
        panelDadoUtilizados = new JPanel();
        panelDadoUtilizados.setPreferredSize(new Dimension(400,250));
        panelDadoUtilizados.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.BLACK, 5),
                "Dados Utilizados", TitledBorder.CENTER, TitledBorder.TOP, new Font("Arial", Font.BOLD, 25), Color.GRAY));
        panelDadoUtilizados.setBackground(new Color(255,255,255,0));

        constraints.gridx=6;
        constraints.gridy=2;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.BOTH;
        constraints.anchor=GridBagConstraints.CENTER;
        add(panelDadoUtilizados,constraints);

        /**
         * Creacion de panel puntaje
         */
        panelPuntaje = new JPanel();
        panelPuntaje.setLayout(new GridLayout(0,1));
        panelPuntaje.setPreferredSize(new Dimension(308,80));
        panelPuntaje.setBorder(BorderFactory.createTitledBorder("Puntaje"));
        panelPuntaje.setBackground(new Color(255,255,255,0));
        panelPuntaje.add(textoPuntajeTotal);
        panelPuntaje.add(textoPuntaje);

        constraints.gridx=3;
        constraints.gridy=3;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.CENTER;
        constraints.anchor=GridBagConstraints.CENTER;
        add(panelPuntaje,constraints);

        /**
         *  Creacion de panel ronda
         */
        panelRonda = new JPanel();
        panelRonda.setPreferredSize(new Dimension(308,50));
        panelRonda.setBorder(BorderFactory.createTitledBorder("Ronda"));
        panelRonda.setBackground(new Color(255,255,255,0));

        constraints.gridx=3;
        constraints.gridy=4;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.CENTER;
        constraints.anchor=GridBagConstraints.CENTER;
        add(panelRonda,constraints);

        /**
         * Creacion de boton "Nuevo dado"
         */
        continuarReiniciar = new JButton();
        continuarReiniciar.setText("Continuar ronda");
        continuarReiniciar.addActionListener(escucha);
        continuarReiniciar.setName("continuarReiniciar");
        continuarReiniciar.setContentAreaFilled(false);
        continuarReiniciar.setBorderPainted(false);
        continuarReiniciar.setOpaque(false);
        continuarReiniciar.setEnabled(false);

        constraints.gridx=1;
        constraints.gridy= 4;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.CENTER;
        constraints.anchor=GridBagConstraints.CENTER;
        add(continuarReiniciar,constraints);

        /**
         * Creación mensaje de atención
         */
        mensajesSalida= new JTextArea(3,30);
        mensajesSalida.setText("Usa el botón (Ayuda) para ver las reglas del juego");
        mensajesSalida.setBorder(BorderFactory.createTitledBorder("Atención: "));
        mensajesSalida.setEditable(false);
        mensajesSalida.setBackground(new Color(255,255,255,0));
        constraints.gridx=6;
        constraints.gridy=5;
        constraints.gridwidth=3;
        constraints.fill=GridBagConstraints.NONE;
        constraints.anchor=GridBagConstraints.CENTER;
        add(mensajesSalida,constraints);

        /**
         * Creación de los Dado
         */
        Model.lanzamientoDado();
        inicializarBotones();
    }

    /**
     * Main process of the Java program
     * @param args Object used in order to send input data from command line when
     *             the program is execute by console.
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            GUIGridBagLayout miProjectGUI = new GUIGridBagLayout();
        });
    }

    /**
     * Inicializa los botones activos e inactivos en sus zonas correspondientes
     */
    public void inicializarBotones(){
        // Inicializacion Dado activos
        for(int dado=0; dado < Model.listaDado("activos").size(); dado++){
            botones.add(new JButton());
            botones.get(dado).setName("dado" + String.valueOf(dado+1));
            botones.get(dado).setBorder(null);
            botones.get(dado).setBackground(Color.white);
            botones.get(dado).addMouseListener(escucha);
            botones.get(dado).setVisible(false);
            imageDado = new ImageIcon(getClass().getResource("/resources/" + Model.getAccionDado("dado" + String.valueOf(botones.size()), "activos") + ".JPG"));
            botones.get(dado).setIcon(new ImageIcon(imageDado.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT)));
            panelDadoActivos.add(botones.get(dado));
        }

        // Inicializacion Dado inactivos
        for(int dado=0; dado < Model.listaDado("inactivos").size(); dado++){
            botonesInactivos.add(new JButton());
            botonesInactivos.get(dado).setName("dado" + String.valueOf(dado+1));
            botonesInactivos.get(dado).setBorder(null);
            botonesInactivos.get(dado).setBackground(Color.white);
            botonesInactivos.get(dado).setVisible(false);
            imageDado = new ImageIcon(getClass().getResource("/resources/Dice.png"));
            botonesInactivos.get(dado).setIcon(new ImageIcon(imageDado.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT)));
            panelDadoInactivos.add(botonesInactivos.get(dado));
        }
    }

    /**
     * Actualiza el panel correspondiente
     * @param nombrePanel
     */
    public void actualizarPanel(String nombrePanel){
        if(nombrePanel == "activos"){
            panelDadoActivos.removeAll();
            for(int boton=0; boton < botones.size(); boton++){
                panelDadoActivos.add(botones.get(boton));
            }
            panelDadoActivos.updateUI();
            repaint();
            revalidate();
        }else{
            if(nombrePanel == "inactivos"){
                panelDadoInactivos.removeAll();
                for(int boton=0; boton < botonesInactivos.size(); boton++){
                    panelDadoInactivos.add(botonesInactivos.get(boton));
                }
                panelDadoInactivos.updateUI();
                repaint();
                revalidate();
            }else{
                panelDadoUtilizados.removeAll();
                for(int boton=0; boton < botonesUtilizados.size(); boton++){
                    panelDadoUtilizados.add(botonesUtilizados.get(boton));
                }
                panelDadoUtilizados.updateUI();
                repaint();
                revalidate();
            }
        }
    }

    /**
     * Renombra los botones cada vez que se elimine o agregue un nuevo boton a un Arraylist
     * @param nombreArray
     */
    public void renombrarBotones(String nombreArray){
        if(nombreArray == "activos"){
            for(int boton=0; boton < botones.size(); boton++){
                botones.get(boton).setName("dado" + String.valueOf(boton+1));
            }
        }else{
            if(nombreArray == "inactivos"){
                for(int boton=0; boton < botonesInactivos.size(); boton++){
                    botonesInactivos.get(boton).setName("dado" + String.valueOf(boton+1));
                }
            }else{
                for(int boton=0; boton < botonesUtilizados.size(); boton++){
                    botonesUtilizados.get(boton).setName("dado" + String.valueOf(boton+1));
                }
            }
        }
    }

    /**
     * Hace pares de nombre y JButton dependiendo del ArrayList y me retorna el JButton
     * @param nombreArray
     * @param nombreDado
     * @return JButton
     */
    public JButton mappingJButton(String nombreArray, String nombreDado){
        if(nombreArray == "activos"){
            for(int boton=0; boton < botones.size(); boton++){
                valorBotones.put(botones.get(boton).getName(), botones.get(boton));
            }
        }else{
            if(nombreArray == "inactivos"){
                for(int boton=0; boton < botonesInactivos.size(); boton++){
                    valorBotones.put(botonesInactivos.get(boton).getName(), botonesInactivos.get(boton));
                }
            }else{
                for(int boton=0; boton < botonesUtilizados.size(); boton++){
                    valorBotones.put(botonesUtilizados.get(boton).getName(), botonesUtilizados.get(boton));
                }
            }
        }
        return valorBotones.get(nombreDado);
    }

    /**
     * Determina la ronda actual, el puntaje de cada ronda y el puntaje total
     */
    public void rondas(){
        int Dado42 = 0; // 42
        int DadoDragon = 0; // dragones
        int seguirLanzando = 0; // 1 si sigue lanzando, de lo contrario 0
        String resultadoPuntaje = "";

        // Mensaje cuando no hay Dado activos en el array
        if(botones.size() == 0){
            puntajeRonda = 0;
            puntaje += puntajeRonda;
            ronda += 1;
            estadoDelJuego = 1;
        }else{
            // Mensaje cuando solo hay un dado activo en el array
            if(botones.size() == 1){
                if(Model.getAccionDado(botones.get(0).getName(), "activos") == "corazon"){
                    nuevoEscucha = 0;
                    estadoDelJuego = 0;
                    seguirLanzando = 1;
                    escuchas();
                }else{
                    if(Model.getAccionDado(botones.get(0).getName(), "activos") == "42"){
                        puntajeRonda = 1;
                        puntaje += puntajeRonda;
                        ronda += 1;
                        estadoDelJuego = 1;
                    }else{
                        if(Model.getAccionDado(botones.get(0).getName(), "activos") == "dragon"){
                            puntajeRonda = 0;
                            puntaje = puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                        }else{
                            puntajeRonda = 0;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                        }
                    }
                }
            }else{
                // Cuenta el total de Dado 42, dragones o si el ultimo dado es un corazon
                for (int boton=0; boton < botones.size(); boton++){
                    if(Model.getAccionDado(botones.get(boton).getName(), "activos") == "42"){
                        Dado42 += 1;
                    }else{
                        if(Model.getAccionDado(botones.get(boton).getName(), "activos") == "dragon"){
                            DadoDragon += 1;
                        }
                    }
                }
                // Si la cantidad de Dado 42 es igual al tamaño del ArrayList, gana
                if(Dado42 == botones.size()){
                    switch (Dado42){
                        case 2:
                            puntajeRonda = 3;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        case 3:
                            puntajeRonda = 6;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        case 4:
                            puntajeRonda = 10;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        case 5:
                            puntajeRonda = 15;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        case 6:
                            puntajeRonda = 21;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        case 7:
                            puntajeRonda = 28;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        case 8:
                            puntajeRonda = 36;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        case 9:
                            puntajeRonda = 45;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        case 10:
                            puntajeRonda = 55;
                            puntaje += puntajeRonda;
                            ronda += 1;
                            estadoDelJuego = 1;
                            break;
                        default:
                            break;
                    }
                }else{
                    // Si la cantidad de dragones y 42 es igual al tamaño del array
                    if(Dado42 + DadoDragon == botones.size()){
                        puntajeRonda = 0;
                        puntaje = 0;
                        ronda += 1;
                        estadoDelJuego = 1;
                    }else{
                        seguirLanzando = 1;
                        estadoDelJuego = 0;
                    }
                }
            }
        }
        // Verifica si se presiono el dragon
        if(dragon == 1){
            puntajeRonda = 0;
            puntaje = puntajeRonda;
            textoPuntajeTotal.setText("Puntaje total: " + String.valueOf(puntaje));
        }
        if(seguirLanzando == 1){
            resultadoPuntaje = "¡Sigue lanzando!";
        }else{
            resultadoPuntaje = "Tu puntaje es: " + String.valueOf(puntajeRonda);
        }
        if(estadoDelJuego == 1){
            for(int boton=0; boton < botones.size(); boton++){
                botones.get(boton).removeMouseListener(escucha);
            }

            // Se limpian los ArrayList de Dado para comenzar una nueva ronda
            Model.listaDado("activos").clear();
            Model.listaDado("inactivos").clear();
            Model.listaDado("utilizados").clear();

            // Se limpian los ArrayList de botones para comenzar una nueva ronda
            botones.clear();
            botonesInactivos.clear();
            botonesUtilizados.clear();

            Model.lanzamientoDado();
            textoPuntajeTotal.setText("Puntaje total: " + String.valueOf(puntaje));
            textoPuntaje.setText(resultadoPuntaje);
            inicializarBotones();

            if(ronda < 6 && puntaje < 29){
                continuarReiniciar.setEnabled(true); // Solo habilita el botón para continuar a la siguiente ronda
            }else{
                if(ronda < 6 && puntaje > 29){
                    resultadoPuntaje = "Tu puntaje es: " + String.valueOf(puntaje) + " ¡Has ganado!";
                }else{
                    resultadoPuntaje = "Tu puntaje es: " + String.valueOf(puntaje) + " ¡Has perdido!";
                }
                puntaje = 0;
                puntajeRonda = 0;
                ronda = 1;
                continuarReiniciar.setText("Jugar de nuevo");
                continuarReiniciar.setEnabled(true);
            }
            dragon = 0;
        }

        textoPuntaje.setText(resultadoPuntaje);
    }

    /**
     * Determina cual evento se esta invocando
     */
    public void escuchas(){
        class GetEscuchas implements MouseListener{
            @Override
            public void mouseClicked(MouseEvent e) {
                switch (nuevoEscucha){
                    case 0:
                        escucha.mouseClicked(e);
                        break;
                    case 1:
                        cambiarImagen.mouseClicked(e);
                        break;
                    case 2:
                        superheroe.mouseClicked(e);
                        break;
                    case 3:
                        corazon.mouseClicked(e);
                        break;
                    case 4:
                        cohete.mouseClicked(e);
                        break;
                    default:
                        break;
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        }
    }

    /**
     * Realiza la accion del dado cohete
     */
    private class AccionCohete implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            String botonSecundario = "";
            botonSecundario = e.getComponent().getName();
            Model.accionCohete(botonSecundario);
            mappingJButton("activos", botonSecundario).setEnabled(false); // Deshabilita el boton
            mappingJButton("activos", botonSecundario).removeMouseListener(this); // Remueve el MouseListener cohete
            botonesInactivos.add(mappingJButton("activos", botonSecundario)); // Adiciona el boton en la lista inactivos
            renombrarBotones("inactivos");
            botones.remove(mappingJButton("activos", botonSecundario)); // Borra el boton de la lista activos
            renombrarBotones("activos");
            actualizarPanel("inactivos");
            actualizarPanel("activos");

            for(int boton=0; boton < botones.size(); boton++){
                botones.get(boton).removeMouseListener(this);
                botones.get(boton).addMouseListener(escucha);
            }

            nuevoEscucha = 0;
            escuchas();
            rondas();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Realiza la accion del dado corazon
     */
    private class AccionCorazon implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            String botonSecundario = "";
            botonSecundario = e.getComponent().getName();
            Model.accionCorazon(botonSecundario);
            botones.add(mappingJButton("inactivos", botonSecundario)); // Adiciona el boton en la lista activos
            renombrarBotones("activos");
            botonesInactivos.remove(mappingJButton("inactivos", botonSecundario)); // Borra el boton de la lista inactivos
            renombrarBotones("inactivos");
            imageDado = new ImageIcon(getClass().getResource("/resources/" + Model.getAccionDado("dado" + String.valueOf(botones.size()), "activos") + ".JPG")); // Al invocar accionCorazon, el dado ingresa de ultimo a la lista de activos
            mappingJButton("activos", "dado" + String.valueOf(botones.size())).setIcon(new ImageIcon(imageDado.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT)));
            actualizarPanel("activos");
            actualizarPanel("inactivos");

            for(int boton=0; boton < botonesInactivos.size(); boton++){
                botonesInactivos.get(boton).removeMouseListener(this);
                botonesInactivos.get(boton).setEnabled(false);
            }

            for(int boton=0; boton < botones.size(); boton++){
                botones.get(boton).removeMouseListener(this);
                botones.get(boton).addMouseListener(escucha);
            }
            nuevoEscucha = 0;
            escuchas();
            rondas();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Realiza la accion del dado Superhéroe
     */
    private class AccionSuperHeroe implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            String botonSecundario = "";
            botonSecundario = e.getComponent().getName();

            // Si el nombre del dado es un superheroe, vuelve a repetir el lanzamiento

                Model.accionSuperHeroe(botonSecundario);
                imageDado = new ImageIcon(getClass().getResource("/resources/" + Model.getAccionDado("dado" + String.valueOf(botones.size()), "activos") + ".JPG"));
                mappingJButton("activos", botonSecundario).setIcon(new ImageIcon(imageDado.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT)));

                for(int boton=0; boton < botones.size(); boton++){
                    botones.get(boton).removeMouseListener(this);
                    botones.get(boton).addMouseListener(escucha);
                }
                nuevoEscucha = 0;
                escuchas();
                rondas();


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Realiza la accion del dado Mepple
     */
    private class CambiarImagen implements MouseListener{

        @Override
        public void mouseClicked(MouseEvent e) {
            String botonSecundario = "";
            botonSecundario = e.getComponent().getName();
            Model.accionMepple(botonSecundario);
            imageDado = new ImageIcon(getClass().getResource("/resources/" + Model.getAccionDado("dado" + String.valueOf(botones.size()), "activos") + ".JPG"));
            mappingJButton("activos", botonSecundario).setIcon(new ImageIcon(imageDado.getImage().getScaledInstance(80,80, Image.SCALE_DEFAULT)));

            for(int boton=0; boton < botones.size(); boton++){
                botones.get(boton).removeMouseListener(this);
                botones.get(boton).addMouseListener(escucha);
            }
            nuevoEscucha = 0;
            escuchas();
            rondas();
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

    /**
     * Evento principal cuando se lanza los Dado y se presiona un dado
     */
    private class Escucha implements ActionListener, MouseListener {

        @Override
        public void actionPerformed(ActionEvent e) {

            if(e.getSource()==lanzar){

                lanzar.setEnabled(false);
                panelDadoActivos.remove(lanzar);
                textoRonda.setText("Ronda: " + String.valueOf(ronda));
                panelRonda.add(textoRonda);
                continuarReiniciar.setText("Continuar ronda");
                textoPuntajeTotal.setText("Puntaje total: " + String.valueOf(puntaje));
                textoPuntaje.setText("¡Usa algún un dado!");

                // Aparecen los Dado activos e inactivos
                for(int dado=0; dado < botones.size(); dado++){
                    botones.get(dado).setVisible(true);
                    repaint();
                    revalidate();
                }

                for(int dado=0; dado < botonesInactivos.size(); dado++){
                    botonesInactivos.get(dado).setVisible(true);
                    botonesInactivos.get(dado).setEnabled(false);
                    repaint();
                    revalidate();
                }

            }else{
                if(e.getSource()==creditos){
                    JOptionPane.showMessageDialog(null,CREDITOS);
                }else{
                    if (e.getSource()==ayuda){
                        JOptionPane.showMessageDialog(null,AYUDA);
                    }else{
                        if (e.getSource()==botonExplicacion){
                            revalidate();
                            repaint();
                            JOptionPane.showMessageDialog(null,BOTON_EXPLICACION);
                        }else{
                            if(e.getSource() == continuarReiniciar){
                                actualizarPanel("activos");
                                actualizarPanel("inactivos");
                                actualizarPanel("utilizados");
                                textoRonda.setText("Ronda: " + String.valueOf(ronda));
                                textoPuntajeTotal.setText("Puntaje total: " + String.valueOf(puntaje));
                                textoPuntaje.setText(null);
                                continuarReiniciar.setEnabled(false);
                                lanzar.setEnabled(true);
                                panelDadoActivos.add(lanzar);
                            }else{
                                System.exit(0);
                            }
                        }
                    }
                }
            }
        }

        /**
         * Mouse clicked para los Dado
         * @param e
         */
        @Override
        public void mouseClicked(MouseEvent e) {

            //Aquí empieza el juego, ya que al dar clic en un dado se va a jugar con este

            String nombreBoton = "";
            String nombreAccion = "";

            nombreBoton = e.getComponent().getName();
            nombreAccion = Model.getAccionDado(nombreBoton, "activos");
            mappingJButton("activos", nombreBoton).setEnabled(false); // Deshabilita el boton despues de presionarlo
            mappingJButton("activos", nombreBoton).removeMouseListener(this);
            botonesUtilizados.add(mappingJButton("activos", nombreBoton));
            renombrarBotones("utilizados"); // Actualiza los nombres de los botones del ArrayList utilizados
            botones.remove(mappingJButton("activos", nombreBoton));
            renombrarBotones("activos"); // Actualiza los nombres de los botones del ArrayList activos
            Model.DadoUtilizados(nombreBoton); // Remueve el dado de la zona de activos y lo mueve a utilizados
            actualizarPanel("activos");
            actualizarPanel("utilizados");
            valorBotones.clear();

            if(nombreAccion == "mepple") {
                for(int boton=0; boton < botones.size(); boton++){
                    botones.get(boton).removeMouseListener(this);
                    botones.get(boton).addMouseListener(cambiarImagen);
                }

                nuevoEscucha = 1;
                textoPuntaje.setText("Accion mepple activado");
                escuchas();
            }else{
                if(nombreAccion == "superheroe") {
                    // Si quedan dos Dado superheroes, se lanza uno sin activar su accion y gana 0 puntos al final de la ronda
                    if(botones.size() == 1 && Model.getAccionDado(botones.get(0).getName(), "activos") == "superheroe"){
                        rondas();
                    }else{
                        for(int boton=0; boton < botones.size(); boton++){
                            botones.get(boton).removeMouseListener(this);
                            botones.get(boton).addMouseListener(superheroe);
                        }

                        textoPuntaje.setText("Accion superheroe activado");
                        nuevoEscucha = 2;
                        escuchas();
                    }
                }else{
                    if(nombreAccion == "dragon") {
                        dragon = 1;
                        nuevoEscucha = 0;
                        puntaje = 0;
                        escuchas();
                        rondas();
                    }else{
                        if(nombreAccion == "corazon") {
                            if(botonesInactivos.size() > 0){
                                for(int boton=0; boton < botones.size(); boton++){
                                    botones.get(boton).removeMouseListener(this);
                                }

                                for(int boton=0; boton < botonesInactivos.size(); boton++){
                                    botonesInactivos.get(boton).setEnabled(true);
                                    botonesInactivos.get(boton).addMouseListener(corazon);
                                }

                                nuevoEscucha = 3;
                                textoPuntaje.setText("Accion corazon activado");
                                escuchas();
                            }else{
                                nuevoEscucha = 0;
                                escuchas();
                                rondas();
                            }
                        }else{
                            if(nombreAccion == "cohete") {
                                for(int boton=0; boton < botones.size(); boton++){
                                    botones.get(boton).removeMouseListener(this);
                                    botones.get(boton).addMouseListener(cohete);
                                }

                                nuevoEscucha = 4;
                                textoPuntaje.setText("Accion cohete activado");
                                escuchas();
                            }else{
                                nuevoEscucha = 0;
                                escuchas();
                                rondas();
                            }
                        }
                    }
                }
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }
}