<template>
  <v-container>
    <v-row justify="end">
      <v-col cols="12" sm="6" md="4" lg="6" xl="8" class="text--secondary font-italic">
<!--        Data based on 15 trips-->
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3" xl="2">
        <v-select
            solo
            :items="[ { text: 'Surface Street', value: 'STREET' }, { text: 'Highway', value: 'HIGHWAY' } ]"
            v-model="type"
            label="Road Type"
        ></v-select>
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3" xl="2">
        <v-select
            solo
            :items="[ { text: 'All conditions', value: null }, { text: 'Day', value: 'DAY' }, { text: 'Night', value: 'NIGHT' } ]"
            v-model="condition"
            label="Condition"
        ></v-select>
      </v-col>
    </v-row>

    <disengagement-chart
        v-if="chartData"
        :chart-data="chartData"
        :options="chartOptions"
        :height="150">
    </disengagement-chart>
  </v-container>
</template>

<script>
import DisengagementChart from "@/components/DisengagementChart";
import axios from "axios";

export default {
  name: 'Home',

  components: {
    DisengagementChart
  },

  data: () => ({
    chartData: null,
    condition: null,
    type: 'STREET',
    chartOptions: {
      layout: {
        padding: {
          left: 10,
          right: 10,
          top: 10,
          bottom: 10
        }
      },
      tooltips: {
        mode: 'nearest',
        position: 'average',
        intersect: false
      },
      legend: {
        display: false,
        position: 'chartArea',
        labels: {
          usePointStyle: true
        }
      },
      scales: {
        xAxes: [{
          scaleLabel: {
            display: false
          },
          gridLines: false,
          type: 'time',
          ticks: {
            display: true,
            padding: 8,
            maxRotation: 45,
            minRotation: 45
          },
          time: {
            minUnit: 'day',
            tooltipFormat: 'DD/MM/YYYY',
            isoWeekday: true,
            displayFormats: {
              day: 'DD/MM/YYYY',
              week: 'DD/MM/YYYY',
              month: 'MM/YYYY',
              quarter: '[T]Q - YYYY',
              year: 'YYYY'
            }
          }
        }],
        yAxes: [{
          scaleLabel: {
            display: false
          },
          gridLines: false,
          ticks: {
            reverse: false,
            display: true,
            beginAtZero: true,
            padding: 8,
            stepSize: 1
          }
        }]
      }
    }
  }),

  watch: {
    condition() {
      this.load()
    },
    type() {
      this.load()
    }
  },

  methods: {
    load() {
      axios.get('/data', {
        params: {
          type: this.type,
          condition: this.condition
        }
      }).then(response => {
        const chartData = {
          datasets: [
            {
              label: 'Simple Disengagements',
              borderColor: '#282828',
              backgroundColor: '#28282855',
              radius: 0,
              hoverRadius: 10,
              fill: true,
              lineTension: 0,
              borderWidth: 2,
              data: []
            }, {
              label: 'Critical Disengagements',
              borderColor: '#cc0000',
              backgroundColor: '#cc000055',
              radius: 0,
              hoverRadius: 10,
              fill: true,
              lineTension: 0,
              borderWidth: 2,
              data: []
            }
          ]
        };

        response.data.forEach(log => {
          chartData.datasets[0].data.push({
            x: log.date,
            y: log.kmSimpleFailure
          });
          chartData.datasets[1].data.push({
            x: log.date,
            y: log.kmCriticalFailure
          });
        });

        this.chartData = chartData;
      })
    }
  },

  created() {
    this.load()
  }
}
</script>
