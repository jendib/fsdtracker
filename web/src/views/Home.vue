<template>
  <v-container>
    <v-row justify="center" v-if="counts">
      <v-col xl="2" class="text-center">
        <div class="green lighten-4 ma-4 pa-4 font-weight-light rounded">
          <div>Distance Traveled</div>
          <div class="text-h2">{{counts.distance}}</div>
          <div>kilometers</div>
        </div>
      </v-col>
      <v-col xl="2" class="text-center">
        <div class="blue lighten-4 ma-4 pa-4 font-weight-light rounded">
          <div>FSD Active For</div>
          <div class="text-h2">{{Math.round(counts.duration / 60)}}</div>
          <div>hours</div>
        </div>
      </v-col>
      <v-col xl="2" class="text-center">
        <div class="red lighten-4 ma-4 pa-4 font-weight-light rounded">
          <div>Critical</div>
          <div class="text-h2">{{counts.criticalFailure}}</div>
          <div>Disengagements</div>
        </div>
      </v-col>
      <v-col xl="2" class="text-center">
        <div class="orange lighten-4 ma-4 pa-4 font-weight-light rounded">
          <div>Simple</div>
          <div class="text-h2">{{counts.simpleFailure}}</div>
          <div>Disengagements</div>
        </div>
      </v-col>
      <v-col xl="2" class="text-center">
        <div class="grey lighten-4 ma-4 pa-4 font-weight-light rounded">
          <div>FSD</div>
          <div class="text-h2">{{counts.versions}}</div>
          <div>Versions</div>
        </div>
      </v-col>
    </v-row>

    <v-row justify="end">
      <v-col cols="12" sm="6" md="4" lg="6" xl="8" class="text--secondary font-italic pa-6">
        Kilometers per disengagement. Higher is better.
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3" xl="2">
        <v-select
            solo
            :items="[ { text: 'Surface Street', value: 'STREET' }, { text: 'Highway', value: 'HIGHWAY' } ]"
            v-model="type"
            label="Road Type"
            hide-details
        ></v-select>
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3" xl="2">
        <v-select
            solo
            :items="[ { text: 'All conditions', value: null }, { text: 'Day', value: 'DAY' }, { text: 'Night', value: 'NIGHT' } ]"
            v-model="condition"
            label="Condition"
            hide-details
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
    counts: null,
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
              label: 'Kilometers per Simple Disengagements',
              borderColor: '#282828',
              backgroundColor: '#28282855',
              radius: 0,
              hoverRadius: 10,
              fill: true,
              lineTension: 0,
              borderWidth: 2,
              data: []
            }, {
              label: 'Kilometers per Critical Disengagements',
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

    axios.get('/data/count')
    .then(response => {
      this.counts = response.data
    })
  }
}
</script>
