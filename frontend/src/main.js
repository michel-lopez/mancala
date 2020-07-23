import axios from "axios"
import Circle from "./circle"
import Vector from "./core/vector"

const PIT_R = 45

const message = document.querySelector("#message")

const canvas = document.querySelector("#stage")
canvas.width = 1000
canvas.height = 600

const circle = new Circle(100, 100)
circle.r = 90

const circle2 = new Circle(860, 100)
circle2.r = 90

const pitLeftOffset = 230
const pitTopOffset = 50
const createCircle = r => (name, x, y) => {
    const circle = new Circle(x, y);
    circle.r = r
    return { name, circle }
}

const createPit = (name, x, y) => createCircle(PIT_R)(name, pitLeftOffset + x * 100, pitTopOffset + y * 100)
const createMancalaPit = createCircle(90)

const pitPositions = [
    createPit("Player1Pit0", 0, 1),
    createPit("Player1Pit1", 1, 1),
    createPit("Player1Pit2", 2, 1),
    createPit("Player1Pit3", 3, 1),
    createPit("Player1Pit4", 4, 1),
    createPit("Player1Pit5", 5, 1),
    createMancalaPit("Player1MancalaPit", 860, 100),

    createPit("Player2Pit0", 5, 0),
    createPit("Player2Pit1", 4, 0),
    createPit("Player2Pit2", 3, 0),
    createPit("Player2Pit3", 2, 0),
    createPit("Player2Pit4", 1, 0),
    createPit("Player2Pit5", 0, 0),
    createMancalaPit("Player2MancalaPit", 100, 100)
]

const updateData = data => {
    const { pits: pitsData, availablePits, gameId, winner } = data

    message.innerHTML = winner ? `Game over. ${winner} wins!` : ""


    return pitPositions.map(pit => {
        const data = pitsData[pit.name]
        const action = !availablePits
            ? undefined
            : availablePits.indexOf(pit.name) > -1 
                ? `/api/game/${gameId}/emptypit/${pit.name}`
                : undefined
        return { ...pit, data, action }
    })
}

let activePits
let allPits
const processResults = ({ data }) => {
    allPits = updateData(data)
    activePits = allPits.filter(pit => pit.action)
}

const request = url => axios.get(url).then(processResults).catch(e => {})
request("/api/lastgame")

const bounds = canvas.getBoundingClientRect();
canvas.addEventListener("click", e => {
    const x = e.clientX - bounds.x
    const y = e.clientY - bounds.y
    const mouse = new Vector(x, y)
    const pits = activePits.filter(({ circle }) => circle.hitTest(mouse))
    if (pits.length) {
        request(pits[0].action)
    }
})

document.querySelector("#newGame").addEventListener("click", e => {
    e.preventDefault()
    request("/api/newgame")
})

const context = canvas.getContext("2d")
const render = pits => {
    context.clearRect(0, 0, canvas.width, canvas.height)
    context.fillStyle = "rgba(255, 255, 255, 0.1)"
    context.strokeStyle = "white"

    pits.forEach(pit => {
        context.fillStyle = pit.action
            ? "rgba(255, 255, 255, 0.6)"
            : "rgba(255, 255, 255, 0.3)"
        pit.circle.render(context)
    })
    
    context.save()
    context.font = "bold 28px Verdana"
    context.textAlign = "center"
    context.textBaseline = "middle"
    context.fillStyle = "blue"
    pits.forEach(pit => {
        const { x, y } = pit.circle.position
        context.fillText(pit.data, x, y)
    })
    context.restore()
}

const animate = () => {
    if (allPits) render(allPits)
    requestAnimationFrame(animate)
}
animate()