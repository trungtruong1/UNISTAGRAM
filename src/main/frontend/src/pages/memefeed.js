
import NavBar from '../components/navbar';
import Title from '../components/Title';
import Meme from '../components/Meme';
import Reaction from '../components/Reactions';
import Dialog from '../components/Dialog';
import { useEffect, useState } from 'react';

function Memefeed() {

    const [listMeme, setListMeme] = useState([]);

    useEffect(() => {
        let ignore = false;

        async function fetchData() {
            if(ignore) return;
            const res = await fetch(`http://localhost:8000/memes/`, {
                method: 'GET',
            });
            const data = await res.json();
            data.sort((a, b) => b.timestamp - a.timestamp);
            setListMeme(data);
        }

        fetchData();
        return () => { ignore = true; }        
    }, []);

    return (
        <>
            <NavBar/>

            <div className='containerFeed'>
                {
                    !listMeme.length?
                        (<></>)
                    :
                        listMeme.map((meme, id, arr) => {
                            return <Meme key={id} author={meme.author} image={meme.image} title={meme.title} timeStamp={meme.timeStamp}/>;
                        })
                }
            </div>
            <div className='memeButtonBar'>
                    <Dialog />
                </div>
        </>
    )
}

export default Memefeed;