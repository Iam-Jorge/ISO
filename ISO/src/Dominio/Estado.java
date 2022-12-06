package Dominio;

public enum Estado {
	disponible("Disponible"),
	ocupado("Ocupado"),
	limpieza("Limpieza"),
	desperfecto("Desperfecto");
	
	
	private String valor;

	Estado(String nuevoValor) {
		this.valor = nuevoValor;
	}

	public static Estado getEstado(String valor) {
        for (Estado estado : Estado.values()) {
            if (estado.valor.equalsIgnoreCase(valor)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("No existe el estado " + valor);
    }
}
