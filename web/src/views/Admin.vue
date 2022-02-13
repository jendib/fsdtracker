<template>
  <v-row justify="center">
    <v-col cols="12" xl="6">
      <v-card flat outlined class="pa-4">
        <v-card-title>New Trip</v-card-title>
        <trip-form :trip="trip" v-on:trip-saved="tripAdded()"></trip-form>
      </v-card>
    </v-col>

    <v-col cols="12" xl="12">
      <v-row justify="center">
        <v-col cols="12">
            <v-card flat outlined class="pa-4">
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
                <template v-slot:item.disengagements="{ item }">
                  <ul>
                    <li class="text-no-wrap">Highway: {{ item.highwaySimpleFailure }} / {{ item.highwayCriticalFailure }}</li>
                    <li class="text-no-wrap">Street: {{ item.streetSimpleFailure }} / {{ item.streetCriticalFailure }}</li>
                  </ul>
                </template>
                <template v-slot:item.interventions="{ item }">
                  <ul>
                    <li class="text-no-wrap">Throttle: {{ item.throttleIntervention }}</li>
                    <li class="text-no-wrap">Cancellation: {{ item.cancellationIntervention }}</li>
                  </ul>
                </template>
                <template v-slot:item.edit="{ item }">
                  <v-btn icon @click="edit(item)">
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                  <v-btn icon @click="remove(item)">
                    <v-icon>mdi-delete</v-icon>
                  </v-btn>
                </template>
              </v-data-table>
            </v-card>
        </v-col>
      </v-row>
    </v-col>

    <v-dialog v-model="editDialog" max-width="1000">
      <v-card class="pa-4">
        <v-card-title>Edit Trip</v-card-title>
        <trip-form :trip="editTrip" v-on:trip-saved="tripEdited()"></trip-form>
      </v-card>
    </v-dialog>
  </v-row>
</template>

<style>
.v-data-table-header th, .v-data-table-header th .v-icon {
  color: #cc0000 !important;
}
</style>

<script>
import axios from "axios";
import TripForm from "@/components/TripForm";

export default {
  name: 'Admin',

  components: {
    TripForm
  },

  data: () => ({
    editDialog: false,
    trip: {},
    editTrip: {},
    trips: [],
    headers: [
      {
        text: 'Date',
        value: 'date'
      },
      {
        text: 'Condition',
        value: 'condition'
      },
      {
        text: 'Location',
        value: 'location'
      },
      {
        text: 'Duration',
        value: 'duration'
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
        text: 'FSD Version',
        value: 'version'
      },
      {
        text: 'Disengagements (Simple / Critical)',
        value: 'disengagements',
        filterable: false,
        sortable: false
      },
      {
        text: 'Interventions',
        value: 'interventions',
        filterable: false,
        sortable: false
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
        highwaySimpleFailure: 0,
        highwayCriticalFailure: 0,
        streetSimpleFailure: 0,
        streetCriticalFailure: 0,
        throttleIntervention: 0,
        cancellationIntervention: 0,
        date: localStorage.tripDate ? localStorage.tripDate : null,
        condition: localStorage.tripCondition ? localStorage.tripCondition : 'DAY',
        version: localStorage.tripVersion ? localStorage.tripVersion : null,
        location: null,
        duration: 0,
        highwayDistance: 0,
        streetDistance: 0
      }
    },

    remove(trip) {
      if (confirm('Really delete this trip?')) {
        axios.delete('/trip/' + trip.id).then(() => this.load())
      }
    },

    edit(trip) {
      this.editDialog = true
      this.editTrip = JSON.parse(JSON.stringify(trip))
    },

    tripAdded() {
      localStorage.tripDate = this.trip.date
      localStorage.tripCondition = this.trip.condition
      localStorage.tripVersion = this.trip.version

      this.newTrip()
      this.load()
    },

    tripEdited() {
      this.editDialog = false
      this.load()
    },

    load() {
      axios.get('/trip').then(response => {
        this.trips = response.data
      })
    }
  }
}
</script>