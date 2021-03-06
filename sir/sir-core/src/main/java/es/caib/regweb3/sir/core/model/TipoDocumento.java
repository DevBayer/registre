package es.caib.regweb3.sir.core.model;


/**
 *
 */
public enum TipoDocumento {

    FORMULARIO("01", "Formulario"),
    DOCUMENTO_ADJUNTO("02", "Documento adjunto al formulario"),
    FICHERO_TECNICO_INTERNO("03", "Fichero técnico interno");

    private final String value;
    private final String name;


    TipoDocumento(String value, String name) {
        this.value = value;
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public String getName() {
        return name;
    }

    public static TipoDocumento getTipoDocumento(String value) {

        if (value != null) {

            for (TipoDocumento e : TipoDocumento.values()) {
                if (value.equals(e.getValue())) return e;
            }

        }

        return null;
    }

}