let rootPath
if (process.env.NODE_ENV === 'production') {
  rootPath = `${window.location.origin}/`
} else {
  rootPath = ''
}
export default {
  rootPath
}
