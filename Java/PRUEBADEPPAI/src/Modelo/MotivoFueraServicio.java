package Modelo;

public class MotivoFueraServicio {

    private String comentario;
    private MotivoTipo motivoTipo;

    public MotivoFueraServicio(String comentario, MotivoTipo motivoTipo) {
        this.comentario = comentario;
        this.motivoTipo = motivoTipo;
    }

    public String getComentario() {
        return comentario;
    }

    public MotivoTipo conocerMotivoTipo() {
        return motivoTipo;
    }
}

