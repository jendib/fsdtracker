<template>
  <v-form v-model="valid">
    <v-container>
      <v-row justify="start">
        <v-col cols="12">
          <v-subheader>Trip Informations</v-subheader>
        </v-col>

        <v-col cols="6" lg="4">
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

        <v-col cols="6" lg="4">
          <v-select
              :items="conditions"
              v-model="trip.condition"
              :rules="[v => !!v || 'Condition is required']"
              label="Condition"
          ></v-select>
        </v-col>

        <v-col cols="6" lg="4">
          <v-select
              :items="locations"
              v-model="trip.location"
              :rules="[v => !!v || 'Location is required']"
              label="Location"
          ></v-select>
        </v-col>

        <v-col cols="6" lg="4">
          <v-text-field
              v-model="trip.duration"
              :rules="[v => !!v || 'Duration is required']"
              type="number"
              label="Duration (min)"
          ></v-text-field>
        </v-col>

        <v-col cols="6" lg="4">
          <v-text-field
              v-model="trip.highwayDistance"
              :rules="[v => !!v || v === 0 || 'Highway Distance is required']"
              type="number"
              label="Highway Distance (km)"
          ></v-text-field>
        </v-col>

        <v-col cols="6" lg="4">
          <v-text-field
              v-model="trip.streetDistance"
              :rules="[v => !!v || v === 0 || 'Street Distance is required']"
              type="number"
              label="Street Distance (km)"
          ></v-text-field>
        </v-col>

        <v-col cols="6" lg="4">
          <v-select
              :items="versions"
              v-model="trip.version"
              :rules="[v => !!v || 'Version is required']"
              label="FSD Version"
          ></v-select>
        </v-col>

        <v-col cols="12">
          <v-subheader>Disengagements and Interventions</v-subheader>
        </v-col>

        <v-col cols="6" lg="3">
          <v-text-field
              v-model="trip.highwaySimpleFailure"
              :rules="[v => !!v || v === 0 || 'Highway Simple Failure is required']"
              type="number"
              label="Highway Simple Failure"
          ></v-text-field>
        </v-col>

        <v-col cols="6" lg="3">
          <v-text-field
              v-model="trip.highwayCriticalFailure"
              :rules="[v => !!v || v === 0 || 'Highway Critical Failure is required']"
              type="number"
              label="Highway Critical Failure"
          ></v-text-field>
        </v-col>

        <v-col cols="6" lg="3">
          <v-text-field
              v-model="trip.streetSimpleFailure"
              :rules="[v => !!v || v === 0 || 'Street Simple Failure is required']"
              type="number"
              label="Street Simple Failure"
          ></v-text-field>
        </v-col>

        <v-col cols="6" lg="3">
          <v-text-field
              v-model="trip.streetCriticalFailure"
              :rules="[v => !!v || v === 0 || 'Street Critical Failure is required']"
              type="number"
              label="Street Critical Failure"
          ></v-text-field>
        </v-col>

        <v-col cols="6" lg="3">
          <v-text-field
              v-model="trip.throttleIntervention"
              :rules="[v => !!v || v === 0 || 'Throttle Intervention is required']"
              type="number"
              label="Throttle Intervention"
          ></v-text-field>
        </v-col>

        <v-col cols="6" lg="3">
          <v-text-field
              v-model="trip.cancellationIntervention"
              :rules="[v => !!v || v === 0 || 'Cancellation Intervention is required']"
              type="number"
              label="Cancellation Intervention"
          ></v-text-field>
        </v-col>
      </v-row>
    </v-container>

    <v-card-actions class="justify-center">
      <v-btn large color="primary" :disabled="!valid" v-on:click="save()">{{trip.id ? 'Save' : 'Add'}} Trip</v-btn>
    </v-card-actions>

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
      'San Diego', 'Carlsbad', 'Oceanside', 'San Marcos', 'La Jolla', 'Kearny Mesa', 'Mission Valley',
      'La Mesa', 'Chula Vista', 'National City', 'Otay Mesa', 'Encinitas', 'Escondido', 'Temecula',
      'Irvine', 'San Clemente', 'Anaheim', 'Santa Ana', 'Menifee', 'Riverside', 'Huntington Beach',
      'San Bernardino', 'Rancho Cucamonga', 'Los Angeles', 'San Fernando', 'Victorville',
      'Hemet',
      'Beaumonte',
      'Moreno Valley',
      'Redlands',
      'Barstow',
      'Baker',
      'Primm',
      'Spring Valley',
      'Paradise',
      'Summerlin',
      'Vista',
      'Poway',
      'East County',
      'Calexico',
      'El Centro'
    ].sort(),
    versions: ['10.8', '10.9', '10.10', '10.11']
  }),

  methods: {
    save() {
      const promise = this.trip.id ?
          axios.put('/trip', this.trip) : axios.post('/trip', this.trip)

      promise
          .then(() => {
            this.snackbar = true;
            this.snackbarText = 'Trip saved successfully';
            this.snackbarColor = 'success';
            this.$emit('trip-saved')
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