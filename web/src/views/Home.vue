<template>
  <v-container>
    <v-row justify="space-between">
      <v-col cols="12" sm="6" md="4" lg="3" xl="2" class="text--secondary font-italic">
        Data based on 15 trips
      </v-col>
      <v-col cols="12" sm="6" md="4" lg="3" xl="2">
        <v-select
            solo
            :items="intervals"
            v-model="interval"
            label="Time"
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

export default {
  name: 'Home',

  components: {
    DisengagementChart
  },

  data: () => ({
    interval: 'Last Week',

    intervals: ['Last Week', 'Last Month', 'All Time', 'Version: 10.8', 'Version: 10.9', 'Version: 10.10'],

    chartData: {
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
          data: [{
            x: '2022-01-02',
            y: 5
          },{
            x: '2022-01-03',
            y: 3
          },{
            x: '2022-01-04',
            y: 2
          },]
        }, {
          label: 'Critical Disengagements',
          borderColor: '#cc0000',
          backgroundColor: '#cc000055',
          radius: 0,
          hoverRadius: 10,
          fill: true,
          lineTension: 0,
          borderWidth: 2,
          data: [{
            x: '2022-01-02',
            y: 3
          },{
            x: '2022-01-03',
            y: 1
          },{
            x: '2022-01-04',
            y: 1
          },]
        }
      ]
    },

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
  })
}
</script>
