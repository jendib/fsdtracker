<template>
  <v-app>
    <v-app-bar
        outlined
        app
        color="primary"
        dark>
      <v-container class="fill-height">
        <router-link class="font-weight-bold text-uppercase white--text text-decoration-none" to="/">
          FSD Tracker
        </router-link>

        <div>
          <v-btn class="ml-10"
                 to="/admin"
                 text>
            Trips
          </v-btn>
        </div>

        <v-spacer></v-spacer>

        <div v-if="isAuthenticated">
          Logged in as <span class="font-weight-bold">{{getProfile.name}}</span>
          <v-btn text v-on:click="logout()">Logout</v-btn>
        </div>
      </v-container>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<style>
</style>

<script>
import {USER_REQUEST} from "@/store/actions/user";
import {AUTH_LOGOUT} from "@/store/actions/auth";
import {mapGetters} from "vuex";

export default {
  name: 'App',

  data: () => ({
    //
  }),

  computed: {
    ...mapGetters(["getProfile", "isAuthenticated", "isProfileLoaded"]),
  },

  methods: {
    logout() {
      this.$store.dispatch(AUTH_LOGOUT).then(() => {
        this.$router.push('/')
      })
    }
  },

  created() {
    if (this.$store.getters.isAuthenticated) {
      this.$store.dispatch(USER_REQUEST);
    }
  }
};
</script>
