<template>
  <v-form v-model="valid">
    <v-container>
      <v-row justify="center">
        <v-col cols="4">
          <v-menu
              v-model="dateMenu"
              :close-on-content-click="false"
              :nudge-right="40"
              offset-y
              min-width="auto">
            <template v-slot:activator="{ on, attrs }">
              <v-text-field
                  v-model="trip.date"
                  :rules="[v => !!v || 'Date is required']"
                  label="Date"
                  prepend-icon="mdi-calendar"
                  readonly
                  v-bind="attrs"
                  v-on="on"
              ></v-text-field>
            </template>
            <v-date-picker
                v-model="trip.date"
                @input="dateMenu = false"
            ></v-date-picker>
          </v-menu>
        </v-col>

        <v-col cols="4">
          <v-select
              :items="conditions"
              v-model="trip.condition"
              :rules="[v => !!v || 'Condition is required']"
              label="Condition"
          ></v-select>
        </v-col>

        <v-col cols="4">
          <v-select
              :items="locations"
              v-model="trip.location"
              :rules="[v => !!v || 'Location is required']"
              label="Location"
          ></v-select>
        </v-col>

        <v-col cols="4">
          <v-text-field
              v-model="trip.duration"
              :rules="[v => !!v || 'Duration is required']"
              type="number"
              label="Duration (min)"
          ></v-text-field>
        </v-col>

        <v-col cols="4">
          <v-text-field
              v-model="trip.highwayDistance"
              :rules="[v => !!v || 'Highway Distance is required']"
              type="number"
              label="Highway Distance (km)"
          ></v-text-field>
        </v-col>

        <v-col cols="4">
          <v-text-field
              v-model="trip.streetDistance"
              :rules="[v => !!v || 'Street Distance is required']"
              type="number"
              label="Street Distance (km)"
          ></v-text-field>
        </v-col>

        <v-col cols="4">
          <v-text-field
              v-model="trip.simpleFailure"
              :rules="[v => !!v || v === 0 || 'Simple Failure is required']"
              type="number"
              label="Simple Failure"
          ></v-text-field>
        </v-col>

        <v-col cols="4">
          <v-text-field
              v-model="trip.criticalFailure"
              :rules="[v => !!v || v === 0 || 'Critical failure is required']"
              type="number"
              label="Critical Failure"
          ></v-text-field>
        </v-col>

        <v-col cols="4">
          <v-select
              :items="versions"
              v-model="trip.version"
              :rules="[v => !!v || 'Version is required']"
              label="FSD Version"
          ></v-select>
        </v-col>

        <v-col cols="12" class="d-flex justify-center">
          <v-btn color="primary" :disabled="!valid" v-on:click="add()">Add Trip</v-btn>
        </v-col>
      </v-row>
    </v-container>

    <v-snackbar v-model="snackbar" :timeout="6000" :color="snackbarColor" bottom>
      {{ snackbarText }}
      <template v-slot:action="{ attrs }">
        <v-btn
            text
            v-bind="attrs"
            @click="snackbar = false">OK</v-btn>
      </template>
    </v-snackbar>
  </v-form>
</template>

<script>

import axios from "axios";

export default {
  name: 'TripForm',

  props: {
    trip: {
      type: Object,
      required: true
    }
  },

  data: () => ({
    valid: false,
    dateMenu: false,
    snackbar: false,
    snackbarColor: 'success',
    snackbarText: '',
    conditions: [
      { text: 'Day', value: 'DAY' },
      { text: 'Night', value: 'NIGHT' }
    ],
    locations: [
      'San Diego', 'Carlsbad', 'Oceanside', 'San Marcos', 'La Jolla', 'Kearny Mesa', 'Mission Valley', 'La Mesa', 'Chula Vista', 'National City', 'Otay Mesa', 'Encinitas', 'Escondido', 'Temecula', 'Irvine', 'San Clemente', 'Anaheim', 'Santa Ana', 'Menifee', 'Riverside', 'Huntington Beach', 'San Bernardino', 'Rancho Cucamonga', 'Los Angeles', 'San Fernando', 'Victorville',
      'Hemet',
      'Beaumonte',
      'Moreno Valley',
      'Redlands',
    ].sort(),
    versions: ['10.8', '10.9', '10.10']
  }),

  methods: {
    add() {
      axios.post('/trip', this.trip)
          .then(() => {
            this.snackbar = true;
            this.snackbarText = 'Trip saved successfully';
            this.snackbarColor = 'success';
            this.$emit('trip-added')
          })
          .catch(() => {
            this.snackbar = true;
            this.snackbarText = 'Error saving trip';
            this.snackbarColor = 'error';
          });
    }
  }
}
</script>