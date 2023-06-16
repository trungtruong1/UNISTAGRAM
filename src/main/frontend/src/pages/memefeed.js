
import NavBar from '../components/navbar';
import Title from '../components/Title';
import Meme from '../components/Meme';
import Reaction from '../components/Reactions';
import Dialog from '../components/Dialog';

function Memefeed() {
    return (
        <>
            <NavBar/>

            <div className='containerFeed'>
                <Meme/>
                <Meme/>
                <Meme/>
                <Meme/>
                <Dialog />
            </div>
        </>
    )
}

export default Memefeed;