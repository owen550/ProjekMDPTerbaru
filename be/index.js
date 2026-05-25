// -------------------------Bagian Ini Wajib Ada !!!-------------------
const express = require('express');
const app = express();;
const port = 3000;
app.use(express.json());
app.use(express.urlencoded({ extended: true }));
app.listen(port, () => {
  console.log(`Server berjalan di http://localhost:${port}`);
});
app.get('/', async(req,res)=>{
    res.status(200).send('Pesan Terkirim');
})
// ---------------------------------------------------------------------
const data = [
    {
        id: 1,
        nama: 'kumar'
    },
    {
        id: 2,
        nama: 'asep'
    },
    {
        id: 3,
        nama: 'yanto'
    },
]

// untuk minta data
app.get('/data', async(req,res)=>{
    res.status(200).json(data);
});

// untuk input data
app.post('/data',async(req,res)=>{
    let id = data.length + 1;
    let nama = req.body.nama;
    data.push({
        id,nama
    })
    res.status(201).send({
        message: 'Berhasil Menambahkan Data'
    })
});
