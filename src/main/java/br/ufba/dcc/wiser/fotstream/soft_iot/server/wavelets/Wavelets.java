package br.ufba.dcc.wiser.fotstream.soft_iot.server.wavelets;


import br.ufba.dcc.wiser.fotstream.soft_iot.server.wavelets.TimeSeries;
import java.util.List;



/**
 * @author ricardo
 *
 */
public interface Wavelets {
	
	public void transform(TimeSeries ts);
	
	public List<Double> inverse();
	
	public List<Double> getCoefficients();
	
	public List<Double> getDetails();

}