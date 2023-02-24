<script>
import axios from 'axios';
import Modal from './components/Modal.vue';



const ROWS_PER_PAGE = 25;

export default {
  components: {
    Modal,
  },
  data() {
    return {
      countries: [],
      searchTerm: '',
      sortDirection: '',
      currentPage: 0,
      modalVisible: false,
      selectedCountry: null,
    }
  },
  computed: {
    filteredCountries() {
      if (!this.searchTerm) {
        return this.countries;
      }
      const searchRegex = new RegExp(this.searchTerm, 'i');
      console.log(searchRegex);
      return this.countries.filter(country => searchRegex.test(country.name.official));
    },
    sortedCountries() {
      const sorted = this.filteredCountries.sort((a, b) => {
        if (a.name.official < b.name.official) return -1;
        if (a.name.official > b.name.official) return 1;
        return 0;
      });
      if (this.sortDirection === 'desc') {
        return sorted.reverse();
      }
      return sorted;
    },
    paginatedCountries() {
      const start = this.currentPage * ROWS_PER_PAGE;
      const end = start + ROWS_PER_PAGE;
      return this.sortedCountries.slice(start, end);
    },
    numPages() {
      return Math.ceil(this.sortedCountries.length / ROWS_PER_PAGE);
    },
  },
  mounted() {
    axios
      .get('https://restcountries.com/v3.1/all')
      .then(response => {
        this.countries = response.data;
      })
      .catch(error => {
        console.log(error);
      });
  },
  methods: {
    sort(prop) {
      if (this.sortDirection === 'asc') {
        this.sortDirection = 'desc';
      } else {
        this.sortDirection = 'asc';
      }
    },
    openModal(country) {
      this.selectedCountry = country;
      this.modalVisible = true;
    },
  },
}

</script>
<template>


  <div style="background: #e4e9f7; font-family: 'Julee', cursive;">
    <div class="bg bg-info px-2 py-2 text-white sticky-top top-0" style="font-family: 'Julee', cursive;  ">
      

      <div class="container rounded bg-blue d-flex justify-content-between" style="position: sticky; top: 3rem; padding-left:24rem;">
      <div class="w-50 my-4">
        <div class="input-group">
          <input class="form-control border-5 rounded" style="text-align: center;" type="text" placeholder="Search by Country name"
            v-model="searchTerm" id="example-search-input">
        </div>
      </div>
      <div class="my-4">
        <button class="btn btn-warning me-2 " @click="sort('name.official')">Sort Ascending </button>
        <button class="btn btn-warning" @click="sort('name.official'); sortDirection = 'desc'">Sort
          Descending</button>
      </div>
    </div>
    
    </div>


    <div class="container container-s mt-1">
      <div v-for="country in paginatedCountries" :key="country.cca3" class="w-100 content bg-white rounded">
        <img :src="country.flags.png" style="width: 100%;" class="rounded-top" height="200" />
        <div style="text-align:center; color:blue;"><a class="btn btn-rounded" @click="openModal(country)">{{country.name.official}}</a></div>
        <div><strong> Official Name: </strong>{{ country.name.official }}</div> 
        <div> <strong>Native Name:</strong> <span v-for="n in country.name.nativeName" :key="n"> {{ n.official }}</span>
        </div>
        <div><strong>AltSpellings Name:</strong> {{ country.altSpellings.join(', ') }}</div>
        <div> <strong>CCA2:</strong> {{ country.cca2 }}</div>
        <div> <strong>CCA3: </strong> {{ country.cca3 }}</div>
        <div><strong> IDD:</strong> <span v-for="(s, index) in country.idd.suffixes" v-bind:key="s"><span v-if="index <= 3">
              {{ country.idd.root }} {{ s }} {{ "\n" }} </span></span> </div>
      </div>
    </div>

    <ul style="padding-bottom: 30px;" class="pagination d-flex justify-content-center align-items-center ">
      <li class="page-item">
        <button class="btn btn-primary d-flex align-items-center px-4 py-3 page-link" :disabled="currentPage === 0" @click="currentPage--"><svg
            xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-arrow-left"
            viewBox="0 0 16 16">
            <path fill-rule="evenodd"
              d="M15 8a.5.5 0 0 0-.5-.5H2.707l3.147-3.146a.5.5 0 1 0-.708-.708l-4 4a.5.5 0 0 0 0 .708l4 4a.5.5 0 0 0 .708-.708L2.707 8.5H14.5A.5.5 0 0 0 15 8z" />
          </svg></button>
      </li>
      <li class="page-item"> <span class="page-link d-flex align-items-center rounded px-4 py-3">{{ currentPage + 1 }}/{{ numPages }}</span></li>
      <li class="page-item">
        <button class="btn btn-primary page-link d-flex align-items-center px-4 py-3" :disabled="currentPage === numPages - 1" @click="currentPage++"><svg
            xmlns="http://www.w3.org/2000/svg" width="20" height="20" fill="currentColor" class="bi bi-arrow-right"
            viewBox="0 0 16 16">
            <path fill-rule="evenodd"
              d="M1 8a.5.5 0 0 1 .5-.5h11.793l-3.147-3.146a.5.5 0 0 1 .708-.708l4 4a.5.5 0 0 1 0 .708l-4 4a.5.5 0 0 1-.708-.708L13.293 8.5H1.5A.5.5 0 0 1 1 8z" />
          </svg></button>
      </li>
    </ul>
    <Modal :country="selectedCountry" :visible="modalVisible" @close="modalVisible = false" />
  </div>
</template>
<style scoped>
.container-s {
  height: 100%;
  display: grid;
  grid-template-columns: auto auto auto;
  grid-gap: 10px;
}

@media screen and (max-width: 1200px) {
  .container-s {
    height: 100%;
    display: grid;
    grid-template-columns: auto;
    grid-gap: 10px;
  }
}
.content a,
div {
  padding-left: 5px;
}

button:focus {
  background-color:orange;
}
</style>
