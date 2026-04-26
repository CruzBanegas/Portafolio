package Modelo;

public class MotivoConComentario{
    private final MotivoTipo motivo;
    private final String comentario;

    public MotivoConComentario(MotivoTipo motivo, String comentario) {
        this.motivo = motivo;
        this.comentario = comentario;
    }

    // Getters
    public MotivoTipo getMotivo() { return motivo; }
    public String getComentario() { return comentario; }
}