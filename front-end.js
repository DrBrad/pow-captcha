var solved = false;
//var nonce, hash;

(function(){
    solveChallenge(pow.challenge, pow.difficulty);
}());

async function solveChallenge(challenge, difficulty = 4){
    const encoder = new TextEncoder();
    let nonce = 0;

    while(true){
        const buffer = await crypto.subtle.digest('SHA-256', encoder.encode(challenge+nonce));

        const hashArray = Array.from(new Uint8Array(buffer));
        const hashHex = hashArray.map(b => b.toString(16).padStart(2, '0')).join('');

        if(hashHex.startsWith('0'.repeat(difficulty))){
            pow.nonce = nonce;
            console.log(pow);
            //window.hash = hashHex;
            //return { nonce, hash: hashHex };
            break;
        }

        nonce++;
    }
}

