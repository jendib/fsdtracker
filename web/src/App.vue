<template>
  <v-app>
    <v-app-bar
        outlined
        app
        color="primary"
        dark>
      <div class="app-title align-self-center text-center font-weight-bold text-uppercase">
        FSD Tracker
      </div>
    </v-app-bar>

    <v-main>
      <router-view/>
    </v-main>
  </v-app>
</template>

<style>
.app-title {
  width: 100%;
}
</style>

<script>
export default {
  name: 'App',

  data: () => ({
    //
  }),
};
</script>
