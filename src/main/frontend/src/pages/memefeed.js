
import NavBar from '../components/navbar';
import Title from '../components/Title';
import Meme from '../components/Meme';
import Reaction from '../components/Reactions';

function Memefeed() {
    return (
        <>
            <NavBar/>

            <div className='containerFeed'>
                <Meme/>
                <Meme/>
                <Meme/>
                <Meme/>
            </div>
        </>
    )
}

export default Memefeed;