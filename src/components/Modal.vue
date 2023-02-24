<template>
    <div class="modal" v-if="visible">
        <div class="modal-background" @click="$emit('close')"></div>
        <div class="modal-card">
            <header class="modal-card-head">
                <p class="modal-card-title">{{ country.name.official }}</p>
                <button class="delete" aria-label="close" @click="$emit('close')"></button>
            </header>
            <section class="modal-card-body">
                <div class="columns">
                    <div class="column">
                        <img :src="country.flags.png" alt="Flag">
                    </div>
                    <div class="column">
                        <div>
                            <div><strong>Official Name:</strong> {{ country.name.official }}</div>
                            <div><strong>Native Name:</strong> <span v-for="(n) in country.name.nativeName"
                                    :key="n">{{ n.official }}</span></div>
                            <div><strong>Alternative Names:</strong> {{ country.altSpellings.join(', ') }}</div>
                            <div><strong>2-Character Code:</strong> {{ country.cca2 }}</div>
                            <div><strong>3-Character Code:</strong> {{ country.cca3 }}</div>
                             
                            <div><strong>IDD:</strong> <span v-for="s in country.idd.suffixes" v-bind:key="s"> {{ country.idd.root }} {{
                                s
                            }}</span> </div>
                          </div>
                    </div>
                    
                </div>
            </section>
        </div>
    </div>
</template>

<script>
export default {
    props: {
        country: {
            type: Object,
            required: true,
        },
        visible: {
            type: Boolean,
            required: true,
        },
    },
};
</script>

<style scoped>
.modal {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    z-index: 9999;
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: rgba(125, 113, 113, 0.5);
}

.modal-background {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;

}

.modal-card {
    max-width: 800px;
    background-color: #fff;
}

.modal-card-head {
    background-color: #3273dc;
    color: #fff;
}

.modal-card-title {
    font-size: 24px;
}

.modal-card-body {
    padding: 1rem;
}

.column {
    display: flex;
    align-items: center;
    justify-content: center;
    flex-direction: column;
}
</style>