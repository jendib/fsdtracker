<script>
import {Line, mixins} from 'vue-chartjs';

const { reactiveProp } = mixins;

  export default {
    extends: Line,
    mixins: [ reactiveProp ],
    props: [ 'options' ],
    mounted () {
      var verticalLinePlugin = {
        id: 'verticalLine',
        afterDraw: function(chartInstance) {
          if (chartInstance.tooltip._active && chartInstance.tooltip._active.length) {
            var activePoint = chartInstance.tooltip._active[0],
              ctx = chartInstance.ctx,
              x = activePoint.tooltipPosition().x,
              topY = chartInstance.legend.bottom,
              bottomY = chartInstance.chartArea.bottom;

            // draw line
            ctx.save();
            ctx.beginPath();
            ctx.setLineDash([5, 5]);
            ctx.moveTo(x, topY);
            ctx.lineTo(x, bottomY);
            ctx.lineWidth = 1;
            ctx.strokeStyle = '#444';
            ctx.stroke();
            ctx.restore();
          }
        }
      };
      this.addPlugin(verticalLinePlugin);
      this.renderChart(this.chartData, this.options)
    }
  }
</script>