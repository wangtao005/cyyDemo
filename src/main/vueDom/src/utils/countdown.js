import NP from 'number-precision'
export function countdown(trim, interval, cd) {
  let trimNum = trim
  let inter = setInterval(() => {
    trimNum = NP.minus(trimNum, 1)
    if (cd) cd(trimNum)
    if (trimNum <= 0) {
      clearInterval(inter)
    }
  }, interval)
}
