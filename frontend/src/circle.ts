import Vector from "./core/vector"

const white = "rgba(255, 255, 255, 0.7)"
const red = "rgba(255, 0, 0, 0.7)"

const defaultR = 30

export default class Circle {
    position:Vector
    r:number = defaultR
    speed:Vector = new Vector()

    constructor(x:number, y:number) {
        this.position = new Vector(x, y)
    }

    update() {
        this.position = this.position.add(this.speed)
    }

    render(context: CanvasRenderingContext2D|OffscreenCanvasRenderingContext2D) {
        const { x, y } = this.position
        context.beginPath()
        context.arc(x, y, this.r, 0, Math.PI * 2)
        context.fill()
        context.stroke()
    }

    hitTest(position:Vector) {
        const dVector = this.position.sub(position)
        const distance = dVector.magnitude()
        return this.r > distance
    }
}