import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login extends  JDialog{
    private JLabel LUsuario;
    private JTextField usuarioTF;
    private JLabel JIniciarSecion;
    private JLabel LContrasenia;
    private JPasswordField passwordTF;
    private JComboBox perfilesCB;
    private JButton ingresarButton;
    private JPanel panelLogin;
    private JLabel ImgIcono;
    private JLabel LEmpresa;
    private JLabel LAstronauta;
    public static String dato;

    public  Login (JFrame parent) {
        super(parent);
        setTitle("Login");
        setContentPane(panelLogin);
        setMinimumSize(new Dimension(640,480));
        setModal(true);

        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setDefaultLookAndFeelDecorated(true);
        ingresarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dato =usuarioTF.getText();
                if(perfilesCB.getSelectedItem().toString()=="ADMINISTRATIVO"){
                    String usuario = usuarioTF.getText();
                    String password = String.valueOf(passwordTF.getPassword());
                    user = getAuthenticationUser(usuario,password);
                    if(user!= null){
                    /*
                    JFrame crud1 = new JFrame("ADMINISTRADOR");
                    crud1.setContentPane(new panelAdministradores().administradores);
                    crud1.setSize(600,500);
                    crud1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    crud1.pack();
                    crud1.setVisible(true);
                    */
                        JOptionPane.showMessageDialog(Login.this,"Usted se ha logrado loguear bienvenido ");
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(Login.this,"Email o Password incorrectos","ERROR",JOptionPane.ERROR_MESSAGE);
                        Limpiar();
                    }
                }

                if(perfilesCB.getSelectedItem().toString()=="ESTUDIANTE"){
                    String idEstudiante = usuarioTF.getText();
                    String  passwordEstudiante= String.valueOf(passwordTF.getPassword());
                    estudiante = getAuthenticationEstudiante(idEstudiante ,passwordEstudiante);
                    if(estudiante!= null){

                    JFrame estudiantePantalla = new JFrame("ESTUDIANTE");
                        estudiantePantalla.setContentPane(new panelEstudiantes().estudiantes);
                        estudiantePantalla.setSize(600,500);
                        estudiantePantalla.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                        estudiantePantalla.pack();
                        estudiantePantalla. setMinimumSize(new Dimension(640,480));
                        estudiantePantalla.setVisible(true);
                        JOptionPane.showMessageDialog(Login.this,"Bienvenido "+estudiante.nombreEstudiante);
                        dispose();
                    }else {
                        JOptionPane.showMessageDialog(Login.this,"Email o Password incorrectos","ERROR",JOptionPane.ERROR_MESSAGE);
                        Limpiar();
                    }
                }

            }
        });
        setVisible(true);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        dispose();
        perfilesCB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

    public Login() {

    }

    public static void main (String[] args){
        Login login = new Login(null);
        //User user = Login.user;
    }
    /* elementos para la autentificación de usuarios */
    public User user;
    private User getAuthenticationUser(String usuario  ,String password){
        User user = null;

        final String DB_URL="jdbc:mysql://localhost/notas?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD= "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="SELECT * FROM users WHERE nombre=? AND password=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,usuario);
            preparedStatement.setString(2,password);
            System.out.println("Conexion ok ");
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                user= new User();
                user.nombre=resultSet.getString("nombre");
                user.email=resultSet.getString("email");
                user.celular=resultSet.getString("celular");
                user.direccion=resultSet.getString("direccion");
                user.password=resultSet.getString("password");
            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            System.out.println("Error de conexion");
            e.printStackTrace();
        }

        return user;
    }

    public Estudiante estudiante;
    private Estudiante getAuthenticationEstudiante(String idEstudiante,String passwordEstudiante){
        Estudiante estudiante = null;

        final String DB_URL="jdbc:mysql://localhost/sistemadenotas?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD= "";
        try{
            Connection conn = DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt=conn.createStatement();
            String sql="SELECT * FROM estudiante WHERE idEstudiante=? AND password=?";
            PreparedStatement preparedStatement=conn.prepareStatement(sql);
            preparedStatement.setString(1,idEstudiante);
            preparedStatement.setString(2,passwordEstudiante);
            System.out.println("Conexion ok ");
            ResultSet resultSet=preparedStatement.executeQuery();
            if(resultSet.next()){
                estudiante= new Estudiante();
                estudiante.idEstudiante=resultSet.getString("idEstudiante");
                estudiante.nombreEstudiante=resultSet.getString("Nombre");
                estudiante.apellidoEstudiante=resultSet.getString("Apellido");
                estudiante.emailEstudiante=resultSet.getString("email");
                estudiante.passwordEstudiante=resultSet.getString("password");
            }
            stmt.close();
            conn.close();
        }catch (Exception e){
            System.out.println("Error de conexion");
            e.printStackTrace();
        }

        return estudiante;
    }

    public void Limpiar(){
        //usuarioTF.setText("");
        passwordTF.setText("");
    }

}

