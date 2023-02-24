import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.sql.*;

public class panelEstudiantes extends JFrame{
    private JTextField cedulaEstudianteTF;
    public JPanel estudiantes;
    private JTable table1;
    private JLabel cedulaTF;
    private JButton presionaAquíParaVerButton;
    public String miDato;

    Connection con;
    Statement st;
    ResultSet rs;
    DefaultTableModel modelo1 = new DefaultTableModel();  // Para la tabla
    public panelEstudiantes (){
        Login ventanaAnterior =new Login();
        miDato=ventanaAnterior.dato;
        try{
            //con= DriverManager.getConnection("jdbc:mysql://mysql-csjurado.alwaysdata.net/csjurado_bdd?serverTimezone=UTC","csjurado","Montufar1996");
            con= DriverManager.getConnection("jdbc:mysql://localhost/sistemadenotas?serverTimezone=UTC","root","");
            st=con.createStatement();
            String s1="select * from calificacion ";
            rs= st.executeQuery(s1);
            while (rs.next()){
                //productosCB.addItem(rs.getString(2));
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,"Error");
        }finally {
            try{
                st.close();
                rs.close();
                con.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,"Error cierre");
            }
        }
        mostrarNotas();
            //BoscarNotas();
        cedulaTF.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
            }
        });
        presionaAquíParaVerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BoscarNotas();
            }
        });
    }
    public Estudiante estudiante;


    public void mostrarNotas(){
        String where ="";
        if(!"".equals(miDato)){
            where = "where Estudiante_idEstudiante ='"+ miDato + "'";
        }
        String cedulaEstudiante = "";
        cedulaTF.setText(miDato);
        System.out.println("Esta es una prueba  x de "+miDato);
        //System.out.println("Esta es una prueba y de "+cedulaTF.getText());
        try{
            table1.setModel(modelo1);
            con= DriverManager.getConnection("jdbc:mysql://localhost/sistemadenotas?serverTimezone=UTC","root","");
            st=con.createStatement();
            String s="select Materia_idMateria,Paralelos_idParalelos,Profesores_idProfesores,calificacion1,calificacion2,calificacion3,Obs from calificacion "+where;
            rs=st.executeQuery(s);
            ResultSetMetaData rsMD = rs.getMetaData();
            int cantidadColumnas = rsMD.getColumnCount();
            modelo1.addColumn("Código");
            modelo1.addColumn("Paralelo");
            modelo1.addColumn("Materia");
            modelo1.addColumn("Nota 1 ");
            modelo1.addColumn("Nota 2 ");
            modelo1.addColumn("Nota 3 ");
            modelo1.addColumn("Observación ");
            while(rs.next()){
                Object [] filas = new Object[cantidadColumnas];
                for(int i=0; i<cantidadColumnas;i++ ){
                    filas[i] =rs.getObject(i+1);
                }
                modelo1.addRow(filas);
            }
        }
        catch (Exception e){
            JOptionPane.showMessageDialog(null,e);
        }finally {
            try{
                st.close();
                rs.close();
                con.close();
            }
            catch(Exception e){
                JOptionPane.showMessageDialog(null,"Error cierre");
            }
        }
    }

    public void BoscarNotas(){
        cedulaTF.setText(miDato);
        System.out.println("Esta es una prueba  x de "+miDato);
        String nombreProducto = "0";
        nombreProducto=miDato;
        final String DB_URL="jdbc:mysql://localhost/sistemadenotas?serverTimezone=UTC";
        final String USERNAME= "root";
        final String PASSWORD= "";
        try{
            Connection conn= DriverManager.getConnection(DB_URL,USERNAME,PASSWORD);
            Statement stmt= conn.createStatement();
            String sql="select * from calificacion where Estudiante_idEstudiante=?";
            PreparedStatement pst=conn.prepareStatement(sql);
            pst.setString(1,nombreProducto);
            ResultSet rs=pst.executeQuery();
            if(rs.next()==true){
                ResultSetMetaData rsMD = rs.getMetaData();
                int cantidadColumnas = rsMD.getColumnCount();
                modelo1.addColumn("Código");
                modelo1.addColumn("Paralelo");
                modelo1.addColumn("Materia");
                modelo1.addColumn("Nota 1 ");
                modelo1.addColumn("Nota 2 ");
                modelo1.addColumn("Nota 3 ");
                modelo1.addColumn("Observación ");
                while(rs.next()){
                    Object [] filas = new Object[cantidadColumnas];
                    for(int i=0; i<cantidadColumnas;i++ ){
                        filas[i] =rs.getObject(i+1);
                    }
                    modelo1.addRow(filas);
                }

            }

            stmt.close();
            conn.close();

        } catch(SQLException ex){
            ex.printStackTrace();
            System.out.println("SQL incorrecto");
        }
    }














}
