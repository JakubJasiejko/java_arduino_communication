import org.apache.log4j.Logger;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Plot extends JFrame {

    private static final Logger logger = Logger.getLogger(Plot.class);

    private final XYSeries series;

    public Plot(String title) {
        super(title);
        series = new XYSeries("Sensor Data");

        XYSeriesCollection dataset = new XYSeriesCollection(series);
        XYPlot plot = new XYPlot(dataset, null, null, null);
        JFreeChart chart = ChartFactory.createXYLineChart(
                "Sensor Data Chart", // chart title
                "X-Axis", // x-axis label
                "Y-Axis", // y-axis label
                dataset, // data
                PlotOrientation.VERTICAL,
                true, // include legend
                true,
                false
        );
        ChartPanel chartPanel = new ChartPanel(chart);

        chartPanel.setPreferredSize(new Dimension(560, 370));
        setContentPane(chartPanel);
        pack();
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public void addDataPoint(double x, double y) {
        series.add(x, y);
        SwingUtilities.updateComponentTreeUI(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Plot plot = new Plot("Sensor Data Chart");
            plot.setVisible(true);

            try {
                // Dodaj opóźnienie przed zakończeniem wątku
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                logger.error("InterruptedException occurred", e);
            }

            // Symulacja dodawania punktów danych w czasie
            double x;
            for (int i = 0; i < 100; i++) {
                x = i;
                double y = Math.random() * 100; // Zastąp to danymi rzeczywistymi
                plot.addDataPoint(x, y);

                try {
                    TimeUnit.MILLISECONDS.sleep(100);
                } catch (InterruptedException e) {
                    logger.error("InterruptedException occurred", e);
                }
            }
        });
    }
}
