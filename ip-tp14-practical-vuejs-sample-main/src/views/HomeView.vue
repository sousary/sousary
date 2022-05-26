<script >
export default {
  components: {},
  data() {
    return {};
  },
  async mounted() {},
  methods: {},
};
</script>

<template>
  <main>
    <div class="bg-gray-100 py-2 text-center text-lg relative">
      TopOne.com

      <div
        class="
          absolute
          text-white
          right-2
          top-2
          bg-gray-400
          px-2
          rounded-md
          cursor-pointer
        "
      >
        <router-link to="/dashboard"><div class="">Dashboard</div></router-link>
      </div>
    </div>
    <h1>You're in Home Page</h1>
  </main>
</template>

<style>
</style>