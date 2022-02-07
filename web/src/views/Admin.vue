<template>
  <div class="mt-8">
    <v-row justify="center">
      <v-col cols="12" xl="6">
        <v-card>
          <v-card-title>New Trip</v-card-title>
          <trip-form :trip="trip" v-on:trip-added="load()"></trip-form>
        </v-card>
      </v-col>

      <v-col cols="12" xl="12">
        <v-row justify="center">
          <v-col cols="12" xl="6">
              <v-card>
                <v-card-title>Trips</v-card-title>

                <v-data-table
                    :headers="headers"
                    :items="trips"
                    sort-by="date"
                    sort-desc
                    fixed-header
                    hide-default-footer
                    disable-pagination>
                  <template v-slot:item.condition="{ item }">
                    {{item.condition.charAt(0).toUpperCase() + item.condition.toLowerCase().slice(1)}}
                  </template>
                  <template v-slot:item.edit="{ item }">
                    <v-btn icon @click="remove(item)">
                      <v-icon>mdi-delete</v-icon>
                    </v-btn>
                  </template>
                </v-data-table>
              </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import axios from "axios";
import TripForm from "@/components/TripForm";

export default {
  name: 'Admin',

  components: {
    TripForm
  },

  data: () => ({
    trip: {},
    trips: [],
    headers: [
      {
        text: 'Date',
        value: 'date'
      },
      {
        text: 'Duration',
        value: 'duration'
      },
      {
        text: 'Condition',
        value: 'condition'
      },
      {
        text: 'Highway Distance',
        value: 'highwayDistance'
      },
      {
        text: 'Street Distance',
        value: 'streetDistance'
      },
      {
        text: 'Simple Failure',
        value: 'simpleFailure'
      },
      {
        text: 'Critical Failure',
        value: 'criticalFailure'
      },
      {
        text: '',
        value: 'edit',
        filterable: false,
        sortable: false
      }
    ],
  }),

  mounted() {
    this.newTrip()
    this.load()
  },

  methods: {
    newTrip() {
      this.trip = {
        simpleFailure: 0,
        criticalFailure: 0,
        location: 'SAN_DIEGO',
        condition: 'DAY'
      }
    },

    remove(trip) {
      axios.delete('/trip/' + trip.id).then(() => this.load())
    },

    load() {
      this.newTrip()

      axios.get('/trip').then(response => {
        this.trips = response.data
      })
    }
  }
}
</script>